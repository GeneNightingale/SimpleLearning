import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Course} from "../entities/course";
import {Lecture} from "../entities/lecture";
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {LectureService} from "../service/lecture.service";
import {Page} from "../entities/page";

@Component({
  selector: 'app-lecture',
  templateUrl: './lecture.component.html',
  styleUrls: ['./lecture.component.css']
})
export class LectureComponent implements OnInit {

  public lecture: Lecture = {
    lectureId: 0,
    title: "default",
    pages: [],
    courseId: 0,
    public: false
  };
  public course: Course = {
    courseId: 0,
    title: "default",
    teacherId: 0,
    description: "default",
    materials: [],
    lectures: [],
    tests: [],
    appeals: [],
  };
  public currentPage: Page = {
    pageId: 0,
    pageNum: 0,
    text: "default"
  }

  public currentPageNum : number = 0;
  public leftButtonText = "Назад до курсу";
  public rightButtonText = "Далі";

  @ViewChild('courseTitle')
  courseTitle: ElementRef | undefined;

  private subscription: Subscription;

  isLoggedIn : boolean = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private lectureService: LectureService) {
    this.subscription = activateRoute.params.subscribe(params=>this.lecture.lectureId=params['lectureId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public getLecture(): void {
    this.lectureService.getLecture(this.lecture.lectureId).subscribe(
      (response: Lecture) => {
        this.lecture = response;
        this.getCourse();
        this.setCurrentPage(this.lecture.pages[0]);
        this.currentPageNum = 0;
        this.initializeButtons();
      }
    );
  }

  public getCourse(): void {
    this.coursesService.getCourse(this.lecture.courseId).subscribe(
      (response: Course) => {
        this.course = response;
      }
    );
  }

  private setCurrentPage(page: Page) {
    this.currentPage = page;
  }

  public prevPage() {
    if(this.currentPageNum === 0){
      this.router.navigate(['/course/'+this.course.courseId]);
    } else {
      this.currentPageNum--;
      this.currentPage = this.lecture.pages[this.currentPageNum];
      this.scrollToTop();
    }
    this.initializeButtons();
  }

  public nextPage() {
    if(this.currentPageNum + 1 === this.lecture.pages.length) {
      this.router.navigate(['/course/'+this.course.courseId]);
    } else {
      this.currentPageNum++;
      this.currentPage = this.lecture.pages[this.currentPageNum];
      this.scrollToTop();
    }
    this.initializeButtons();
  }

  public initializeButtons() {
    if (this.currentPageNum === 0) {
      this.leftButtonText = "Назад до курсу"
    } else {
      this.leftButtonText = "Назад"
    }

    if (this.currentPageNum + 1 == this.lecture.pages.length) {
      this.rightButtonText = "Завершити лекцію"
    } else {
      this.rightButtonText = "Далі"
    }
  }

  ngOnInit(): void {
    this.getLecture();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  scrollToTop(){
    // @ts-ignore
    this.courseTitle.nativeElement.scrollIntoView({behavior: 'smooth'});
  }
}
