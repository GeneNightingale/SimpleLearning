import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { HttpClientModule} from "@angular/common/http";
import { HomePageComponent } from './home-page/home-page.component';
import { AuthorizationComponent } from "./authorization/authorization.component";
import { RegistrationComponent } from "./registration/registration.component";
import { NotFoundComponent } from "./not-found/not-found.component";
import { FooterComponent } from './footer/footer.component';
import {ReactiveFormsModule} from "@angular/forms";
import { authInterceptorProviders } from './helpers/auth.interceptor';
import { DataSharingService } from './service/datasharing.service';
import { CoursesComponent } from './courses/courses.component';
import { CourseComponent } from './course/course.component';
import { LectureComponent } from './lecture/lecture.component';
import { CoursesTeacherComponent } from './courses-teacher/courses-teacher.component';
import { CourseMembersComponent } from './course-members/course-members.component';
import { CourseTeacherComponent } from './course-teacher/course-teacher.component';
import { LectureEditComponent } from './lecture-edit/lecture-edit.component';
import { TestEditComponent } from './test-edit/test-edit.component';
import { TestComponent, FormatTimePipe } from './test/test.component';
import { CoursePerformanceComponent } from './course-performance/course-performance.component';
import { StudentPerformanceComponent } from './student-performance/student-performance.component';

@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    HomePageComponent,
    AuthorizationComponent,
    RegistrationComponent,
    NotFoundComponent,
    FooterComponent,
    CoursesComponent,
    CourseComponent,
    LectureComponent,
    CoursesTeacherComponent,
    CourseMembersComponent,
    CourseTeacherComponent,
    LectureEditComponent,
    TestEditComponent,
    TestComponent,
    FormatTimePipe,
    CoursePerformanceComponent,
    StudentPerformanceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ],
  providers: [authInterceptorProviders, DataSharingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
