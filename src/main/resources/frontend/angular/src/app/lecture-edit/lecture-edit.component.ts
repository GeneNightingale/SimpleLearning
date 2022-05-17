import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {LectureService} from "../service/lecture.service";
import {Course} from "../entities/course";
import {Lecture} from "../entities/lecture";
import {LectureModel} from "../model/lectureModel";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Page} from "../entities/page";
import {PageModel} from "../model/pageModel";

@Component({
  selector: 'app-lecture-edit',
  templateUrl: './lecture-edit.component.html',
  styleUrls: ['./lecture-edit.component.css']
})
export class LectureEditComponent implements OnInit {

  public lecture: Lecture = {
    lectureId: 0,
    title: "default",
    courseId: 0,
    pages: [],
    public: false
  };

  formLecture: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(60)]),
  });
  get title() {
    return this.formLecture.get('title');
  }
  lectureModel: LectureModel = new LectureModel('', 0);
  public courseTitle: string = 'default';

  public isInEditMode: boolean = false;

  private subscription: Subscription;

  isLoggedIn : boolean = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
              private lectureService: LectureService, private changeDetectorRef: ChangeDetectorRef) {
    this.subscription = activateRoute.params.subscribe(params=>this.lecture.lectureId=params['lectureId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public currentPage : any = 1;

  public pagesModel: PageModel[] = [
    {
      pageId: 0,
      pageNum: 1,
      text: ''
    }
  ]; //List of page models for the editor

  ngOnInit(): void {
    this.getLecture();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (!this.dataSharingService.isTeacher && this.dataSharingService.isStudent) {
      this.router.navigate(['/lecture/' + this.lecture.courseId]);
    }
  }
  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  reloadPage() : void {
    window.location.reload();
  }

  //Lecture
  public getLecture(): void {
    this.lectureService.getLecture(this.lecture.lectureId).subscribe(
      (response: Lecture) => {
        this.lecture = response;
        if (this.lecture.title != null) {
          this.lectureModel.title = this.lecture.title;
        }
        if (this.lecture.courseId != null) {
          this.lectureModel.courseId = this.lecture.courseId;
          this.getCourse(this.lecture.courseId);
        }
        if (response.pages.length === 0){
          this.createEmptyPage();
        } else {
          this.pagesModel = response.pages;
          var num : number = 1;
          this.pagesModel.forEach(function (item) {
            item.pageNum = num;
            num++;
            console.log(item);
          });
        }
        this.changeDetectorRef.detectChanges();
      }
    );
  }

  public getCourse(courseId : number): void {
    this.coursesService.getCourse(courseId).subscribe(
      (response: Course) => {
        this.courseTitle = response.title;
      }
    );
  }

  uneditLecture() {
    (<HTMLInputElement>document.getElementById('title')).value = this.lecture.title;
    this.isInEditMode = false;
  }
  editLecture() {
    this.isInEditMode = true;
  }

  submitLecture() {
    const lectureTitle = (<HTMLInputElement>document.getElementById('title'));
    this.lectureModel.title = lectureTitle.value;
    this.lectureService.updateLecture(this.lecture.lectureId, this.lectureModel).subscribe(
      (response: void) => {
        this.savePage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  createEmptyPage() {
    var emptyPage : Page = {
      pageId: 0,
      pageNum: 1,
      text: "Текст лекции"
    }
    this.lectureService.addEmptyPage(this.lecture.lectureId, emptyPage).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  pickedOption(event : any) : void {
    console.log("currentPage" + this.currentPage);
    this.lectureService.updatePage(this.pagesModel[this.currentPage-1]).subscribe(
      (response: void) => {
        this.currentPage = event.value;
        this.getLecture();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteCurrentPage() {
    if(confirm("Точно удалить текущую страницу?")) {
      this.lectureService.deletePage(this.pagesModel[this.currentPage - 1].pageId).subscribe(
        (response: void) => {
          this.currentPage--;
          this.getLecture();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  addPage() {
    var page : Page = {
      pageId: 0,
      pageNum: this.lecture.pages.length+1,
      text: "Текст лекции"
    }
    this.lectureService.addEmptyPage(this.lecture.lectureId, page).subscribe(
      (response: void) => {
        this.getLecture();
        this.currentPage = page.pageNum;
        this.changeDetectorRef.detectChanges();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  nextPage() {
    this.lectureService.updatePage(this.pagesModel[this.currentPage-1]).subscribe(
      (response: void) => {
        this.currentPage++;
        this.getLecture();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  prevPage() {
    this.lectureService.updatePage(this.pagesModel[this.currentPage-1]).subscribe(
      (response: void) => {
        this.currentPage--;
        this.getLecture();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  savePage() {
    this.lectureService.updatePage(this.pagesModel[this.currentPage-1]).subscribe(
      (response: void) => {
        this.uneditLecture();
        this.getLecture();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
