import { Component, OnInit } from '@angular/core';
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {Router} from "@angular/router";
import {ActivatedRoute} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {Course} from "../entities/course";
import {Subscription} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CourseModel} from "../model/courseModel";
import {HttpErrorResponse} from "@angular/common/http";
import {MaterialModel} from "../model/materialModel";
import {MaterialsService} from "../service/materials.service";
import {LectureService} from "../service/lecture.service";
import {TestService} from "../service/test.service";
import {LectureModel} from "../model/lectureModel";
import {TestModel} from "../model/testModel";

@Component({
  selector: 'app-course-teacher',
  templateUrl: './course-teacher.component.html',
  styleUrls: ['./course-teacher.component.css']
})

export class CourseTeacherComponent implements OnInit {

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

  form: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]),
  });
  get title() {
    return this.form.get('title');
  }
  courseModel: CourseModel = new CourseModel('', 0, '');

  formMaterial: FormGroup = new FormGroup({
    materialTitle: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(120)]),
    materialLink: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(120)]),
  });
  get materialTitle() {
    return this.formMaterial.get('materialTitle');
  }
  get materialLink() {
    return this.formMaterial.get('materialLink');
  }
  material: MaterialModel = new MaterialModel( '', this.course.courseId, '');

  formLecture: FormGroup = new FormGroup({
    lectureTitle: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(60)]),
  });
  get lectureTitle() {
    return this.formLecture.get('lectureTitle');
  }
  lecture: LectureModel = new LectureModel( '', this.course.courseId);

  formTest: FormGroup = new FormGroup({
    testTitle: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(60)]),
  });
  get testTitle() {
    return this.formTest.get('testTitle');
  }
  test: TestModel = new TestModel( '', this.course.courseId, 1800);

  public isInEditMode: boolean = false;
  public isInAddMatMode: boolean = false;
  public isInAddLecMode: boolean = false;
  public isInAddTestMode: boolean = false;

  private subscription: Subscription;

  isLoggedIn : boolean = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
              private materialsService: MaterialsService, private lectureService: LectureService,
              private testService: TestService) {
    this.subscription = activateRoute.params.subscribe(params=>this.course.courseId=params['courseId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public getCourse(): void {
    this.coursesService.getCourse(this.course.courseId).subscribe(
      (response: Course) => {
        this.course = response;
        if (this.course.title != null) {
          this.courseModel.title = this.course.title;
        }
        if (this.course.description != null) {
          this.courseModel.description = this.course.description;
        }
      }
    );
  }


  getCourseStudents(courseId: number) {
    this.router.navigate(['/course-members/' + courseId]);
  }

  getCoursePerformance(courseId: any) {
    this.router.navigate(['/course-performance/' + courseId]);
  }

  ngOnInit(): void {
    this.getCourse();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (!this.dataSharingService.isTeacher && this.dataSharingService.isStudent) {
      this.router.navigate(['/course/' + this.course.courseId]);
    }
  }
  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  /*Course controls*/
  editCourse() {
    this.isInEditMode = true;
  }
  uneditCourse() {
    (<HTMLInputElement>document.getElementById('title')).value = this.course.title;
    this.isInEditMode = false;
  }

  submitCourse(courseId: number) {
    const courseTitle = (<HTMLInputElement>document.getElementById('title'));
    const courseDesc = (<HTMLInputElement>document.getElementById('description'));
    this.courseModel.title = courseTitle.value;
    this.courseModel.description = courseDesc.value;
    this.coursesService.updateCourse(this.course.courseId, this.courseModel).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  //Show-hide course
  hideCourse(courseId: number) {
    this.coursesService.makePrivate(courseId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make course private");
      }
    );
  }
  showCourse(courseId: number) {
    this.coursesService.makePublic(courseId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make course publuc");
      }
    );
  }


  /*Material controls*/
  deleteMaterial(materialId: any) {
    if(confirm("Точно видалити веб-ресурс?")) {
      this.materialsService.deleteMaterial(materialId).subscribe(
        (response: void) => {
          this.reloadPage();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  addMaterial() {
    this.isInAddMatMode = true;
  }
  unaddMaterial() {
    this.isInAddMatMode = false;
  }

  createMaterial() {
    this.material.courseId = this.course.courseId;
    this.materialsService.addMaterial(this.material).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //Show-hide material
  hideMaterial(materialId: any) {
    this.materialsService.makePrivate(materialId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make material private");
      }
    );
  }
  showMaterial(materialId: any) {
    this.materialsService.makePublic(materialId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make material public");
      }
    );
  }


  /*Lecture controls*/
  deleteLecture(lectureId: any) {
    if(confirm("Точно видалити лекцiю?")) {
      this.lectureService.deleteLecture(lectureId).subscribe(
        (response: void) => {
          this.reloadPage();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  addLecture() {
    this.isInAddLecMode = true;
  }
  unaddLecture() {
    this.isInAddLecMode = false;
  }

  editLecture(lectureId: any) {
    this.router.navigate(['/lecture-edit/' + lectureId]);
  }

  createLecture() {
    this.lecture.courseId = this.course.courseId;
    this.lectureService.addLecture(this.lecture).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //Show-hide lecture
  hideLecture(lectureId: any) {
    this.lectureService.makePrivate(lectureId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make lecture private");
      }
    );
  }
  showLecture(lectureId: any) {
    this.lectureService.makePublic(lectureId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make lecture public");
      }
    );
  }


  /*Test Controls*/
  deleteTest(testId: any) {
    if(confirm("Точно видалити тест?")) {
      this.testService.deleteTest(testId).subscribe(
        (response: void) => {
          this.reloadPage();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  editTest(testId: any) {
    this.router.navigate(['/test-edit/' + testId]);
  }

  addTest() {
    this.isInAddTestMode = true;
  }
  unaddTest() {
    this.isInAddTestMode = false;
  }

  createTest() {
    this.test.courseId = this.course.courseId;
    this.testService.addTest(this.test).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //Show-hide test
  hideTest(testId: any) {
    this.testService.makePrivate(testId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make test private");
      }
    );
  }
  showTest(testId: any) {
    this.testService.makePublic(testId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make test public");
      }
    );
  }


  reloadPage() : void {
    window.location.reload();
  }

}
