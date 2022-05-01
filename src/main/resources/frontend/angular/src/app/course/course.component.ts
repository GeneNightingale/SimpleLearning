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
import {User} from "../entities/user";
import {Subscription} from "rxjs";
import {Test} from "../entities/test";
import {TestService} from "../service/test.service";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})

export class CourseComponent implements OnInit {

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
  public tests: Test[] = [];
  public teacher: User = {};
  private subscription: Subscription;

  isLoggedIn : boolean = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
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
        this.getTeacherById(this.course.teacherId);
        this.getTestsByCourseId(this.course.courseId);
        if (this.course.public === false)
          this.router.navigate(['/courses']);
        else
          console.log(this.course.public);
      }
    );
  }

  public getTeacherById(teacherId: number): void {
    this.userService.getUserById(teacherId).subscribe(
      (response: User) => {
        this.teacher = response;
      }
    );
  }

  ngOnInit(): void {
    this.getCourse();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (this.dataSharingService.isTeacher && !this.dataSharingService.isStudent) {
      this.router.navigate(['/course-teacher/' + this.course.courseId]);
    }
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  openTest(testId: any) {
    if(confirm("Начать этот тест? Закончив тест, его можно будет пройти заново только получив разрешение от преподавателя.")) {
      this.router.navigate(['/test/' + testId]);
    }
  }

  private getTestsByCourseId(courseId: number) {
    this.testService.getTestsByCourseId(courseId).subscribe(
      (response: Test[]) => {
        this.tests = response;
      }
    );
  }
}
