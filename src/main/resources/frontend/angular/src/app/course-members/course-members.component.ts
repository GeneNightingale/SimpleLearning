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

@Component({
  selector: 'app-course-members',
  templateUrl: './course-members.component.html',
  styleUrls: ['./course-members.component.css']
})
export class CourseMembersComponent implements OnInit {

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
              private studentMembershipService : StudentMembershipService) {
    this.subscription = activateRoute.params.subscribe(params=>this.course.courseId=params['courseId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public memberStudents: User[] = [];
  public notMemberStudents: User[] = [];

  public getCourse(): void {
    this.coursesService.getCourse(this.course.courseId).subscribe(
      (response: Course) => {
        this.course = response;
        this.getMemberStudents(this.course.courseId);
        this.getNotMemberStudents(this.course.courseId);
      }
    );
  }

  public getMemberStudents(courseId : number): void {
    this.userService.getMemberStudents(courseId).subscribe(
      (response: User[]) => {
        this.memberStudents = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getNotMemberStudents(courseId : number): void {
    this.userService.getNotMemberStudents(courseId).subscribe(
      (response: User[]) => {
        this.notMemberStudents = response;
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

  addToCourse(studentId: any) {
    if(confirm("Точно дати цьому студенту доступ до курсу?")) {
      this.studentMembershipService.addMemberToCourse(this.course.courseId, studentId).subscribe(
        data => {
          this.reloadPage();
        },
        err => {
          console.log("Failed at adding a member of ID " + studentId + " to course by ID " + this.course.courseId);
        }
      );
    }
  }

  removeFromCourse(studentId: any) {
    if(confirm("Точно зняти цього студента з курсу? Це також зітре всю його успішність за цим курсом!")) {
      this.studentMembershipService.removeMemberFromCourse(this.course.courseId, studentId).subscribe(
        data => {
          this.reloadPage();
        },
        err => {
          console.log("Failed at removing a member of ID " + studentId + " from course by ID " + this.course.courseId);
        }
      );
    }
  }

  openReportOnStudent(studentId: any) {
    this.router.navigate(['/student-performance/' + studentId]);
  }

  reloadPage() : void {
    window.location.reload();
  }
}
