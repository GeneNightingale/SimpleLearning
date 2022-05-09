import { Component, OnInit } from '@angular/core';
import {Course} from "../entities/course";
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../entities/user";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {StudentMembershipService} from "../service/student-membership";
import {Mark} from "../entities/mark";
import {TestService} from "../service/test.service";
import {TestResults} from "../entities/testResults";
import {Result} from "../entities/result";

@Component({
  selector: 'app-course-performance',
  templateUrl: './course-performance.component.html',
  styleUrls: ['./course-performance.component.css']
})
export class CoursePerformanceComponent implements OnInit {

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

  private subscription: Subscription;

  isLoggedIn : boolean = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
              private studentMembershipService : StudentMembershipService,
              private testService : TestService) {
    this.subscription = activateRoute.params.subscribe(params=>this.course.courseId=params['courseId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public memberStudents: User[] = [];

  public marks: Mark[] = [];
  public testResults: TestResults[] = [];

  public getCourse(): void {
    this.coursesService.getCourse(this.course.courseId).subscribe(
      (response: Course) => {
        this.course = response;
        this.getMemberStudents(this.course.courseId);
      }
    );
  }

  public getMemberStudents(courseId : number): void {
    this.userService.getMemberStudents(courseId).subscribe(
      (response: User[]) => {
        this.memberStudents = response;
        let countTests = 0;
        this.course.tests?.forEach(test => {
          this.testService.getResultsByTestId(test.testId).subscribe(
              (response: Result[]) => {
                this.testResults.push({name: test.title, marks: response})
                this.calculateScores(countTests);
                countTests++;
              })
        })
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
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
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  calculateScores(countTests: number) : void {
    if (countTests === 0)
      this.memberStudents.forEach(student =>
        this.marks.push({
          student: "",
          score: 0})
      );
    console.log(this.marks);
    let count = 0;
      this.testResults[countTests].marks.forEach(mark => {
        this.marks[count].student = mark.name;
        if (mark.score !== -1)
          this.marks[count].score = this.marks[count].score + mark.score;
        count++;
      })
  }

  openReportOnStudent(studentId: any) {
    //TODO: Open report with student's results from this course or your courses in general once that's implemented
  }

  reloadPage() : void {
    window.location.reload();
  }
}
