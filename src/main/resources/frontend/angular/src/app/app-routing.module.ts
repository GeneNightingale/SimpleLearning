import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomePageComponent} from "./home-page/home-page.component";
import {RegistrationComponent} from "./registration/registration.component";
import {AuthorizationComponent} from "./authorization/authorization.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {CoursesComponent} from "./courses/courses.component";
import {CourseComponent} from "./course/course.component";
import {LectureComponent} from "./lecture/lecture.component";
import {CoursesTeacherComponent} from "./courses-teacher/courses-teacher.component";
import {CourseMembersComponent} from "./course-members/course-members.component";
import {CourseTeacherComponent} from "./course-teacher/course-teacher.component";
import {LectureEditComponent} from "./lecture-edit/lecture-edit.component";
import {TestEditComponent} from "./test-edit/test-edit.component";
import {TestComponent} from "./test/test.component";

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component:HomePageComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'authorization', component: AuthorizationComponent},
  {path: 'courses', component: CoursesComponent},
  {path: 'my-courses', component: CoursesTeacherComponent},
  {path: 'course/:courseId', component: CourseComponent },
  {path: 'lecture/:lectureId', component: LectureComponent },
  {path: 'lecture-edit/:lectureId', component: LectureEditComponent },
  {path: 'test/:testId', component: TestComponent },
  {path: 'test-edit/:testId', component: TestEditComponent },
  {path: 'course-members/:courseId', component: CourseMembersComponent },
  {path: 'course-teacher/:courseId', component: CourseTeacherComponent },
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
