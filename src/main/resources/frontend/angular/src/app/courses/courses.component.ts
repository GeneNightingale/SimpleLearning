import { Component, OnInit } from '@angular/core';
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {Course} from "../entities/course";
import {HttpErrorResponse} from "@angular/common/http";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {User} from "../entities/user";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})

export class CoursesComponent implements OnInit {

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private courseService: CoursesService, private userService: UserService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
    this.courses = [];
    this.teachers = [];
  }

  public courses: Course[] = [];
  public teachers: User[] = [];

  isLoggedIn = false;

  public getCourses(): void {
    this.courseService.getMyCourses().subscribe(
      (response: Course[]) => {
        this.courses = response;
        this.initializeTeachers();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getTeacherById(userId: number, i: number): void {
    this.userService.getUserById(userId).subscribe(
      (response: User) => {
        this.teachers[i] = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public initializeTeachers(): void {
    this.teachers = []
    for (let i = 0; i < this.courses.length; i++) {
      this.getTeacherById(this.courses[i].teacherId, i);
    }
  }

  ngOnInit(): void {
    this.getCourses();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (this.dataSharingService.isTeacher && !this.dataSharingService.isStudent) {
      this.router.navigate(['/my-courses']);
    }
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

}
