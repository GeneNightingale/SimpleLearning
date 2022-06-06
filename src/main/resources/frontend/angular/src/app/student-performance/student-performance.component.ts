import { Component, OnInit } from '@angular/core';
import {Course} from "../entities/course";
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {StudentMembershipService} from "../service/student-membership";
import {TestService} from "../service/test.service";
import {TestResults} from "../entities/testResults";
import {Result} from "../entities/result";
import {User} from "../entities/user";

@Component({
  selector: 'app-student-performance',
  templateUrl: './student-performance.component.html',
  styleUrls: ['./student-performance.component.css']
})
export class StudentPerformanceComponent implements OnInit {

  public courses: Course[] = [];

  public studentId: number = 0;
  public studentName: string = 'default';

  private subscription: Subscription;

  isLoggedIn : boolean = false;
  public isStudent = true;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              public dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
              private studentMembershipService : StudentMembershipService,
              private testService : TestService) {
    this.subscription = activateRoute.params.subscribe(params=>this.studentId=params['userId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public courseResults: TestResults[] = [];

  public getCourses(): void {
    if (this.isStudent)
      this.coursesService.getMyCourses().subscribe(
        (response: Course[]) => {
          this.courses = response;
          this.getResults();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    else
      this.coursesService.getCoursesByStudent(this.studentId).subscribe(
        (response: Course[]) => {
          this.courses = response;
          this.getResults();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  public getResults(): void {
    let countCourses = 0;
    this.courses.forEach(course => {
      this.testService.getMyResultsByCourseId(course.courseId, this.studentId).subscribe(
        (response: Result[]) => {
          this.courseResults.push({courseId: course.courseId, name: course.title, marks: response})
          countCourses++;
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        })
    })

  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  ngOnInit(): void {
    //this.getCourses();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
      this.isStudent = this.tokenStorage.getUser().role == "STUDENT";
    }
    this.getCourses();
    if (this.isStudent) {
      this.studentId = this.tokenStorage.getUser().userId;
      this.studentName = this.getCurrentUser();
    } else {
      this.userService.getUserById(this.studentId).subscribe(
        (response: User) => {
          this.studentName = response.name!;
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  reloadPage() : void {
    window.location.reload();
  }
}
