import { Component, OnInit } from '@angular/core';
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CourseModel} from "../model/courseModel";
import {Course} from "../entities/course";
import {HttpErrorResponse} from "@angular/common/http";
import {CoursesService} from "../service/courses.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-courses-teacher',
  templateUrl: './courses-teacher.component.html',
  styleUrls: ['./courses-teacher.component.css']
})

export class CoursesTeacherComponent implements OnInit {

  form: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]),
    description: new FormControl('', [Validators.maxLength(8192)])
  });
  course: CourseModel = new CourseModel('', 0, '');
  get title() {
    return this.form.get('title');
  }
  get description() {
    return this.form.get('description');
  }

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private courseService: CoursesService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
    this.courses = [];
  }

  public courses: Course[] = [];

  isLoggedIn = false;

  //Gets all courses under current teacher
  public getCourses(): void {
    this.courseService.getMyCourses().subscribe(
      (response: Course[]) => {
        this.courses = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }



  //Deletes a course by ID, then reloads page
  deleteCourse(courseId: number) {
    //TODO: Make a better popup
    var courseToDelete : Course | undefined;
    this.courseService.getCourse(courseId).subscribe(
      (response: Course) => {
        courseToDelete = response;
        if(courseToDelete != null && confirm("Точно удалить курс '"+ courseToDelete.title +"'?")) {
          this.deleteCourseById(courseId);
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  deleteCourseById(courseId: number) {
    this.courseService.deleteCourse(courseId).subscribe(
      (response: void) => {
        this.reloadPage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  getCourseStudents(courseId: number) {
    this.router.navigate(['/course-members/' + courseId]);
  }

  editCourse(courseId: number) {
    this.router.navigate(['/course-teacher/' + courseId]);
  }

  //Adds course under current teacher
  createCourse() {
    this.courseService.addCourse(this.course).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Failed at creating a course");
      }
    );
  }

  //Show-hide course
  hideCourse(courseId: number) {
    this.courseService.makePrivate(courseId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make course private");
      }
    );
  }
  showCourse(courseId: number) {
    this.courseService.makePublic(courseId).subscribe(
      data => {
        this.reloadPage();
      },
      err => {
        console.log("Can't make course publuc");
      }
    );
  }


  ngOnInit(): void {
    this.getCourses();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (!this.dataSharingService.isTeacher && this.dataSharingService.isStudent) {
      this.router.navigate(['/courses']);
    }
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  reloadPage() : void {
    window.location.reload();
  }

}
