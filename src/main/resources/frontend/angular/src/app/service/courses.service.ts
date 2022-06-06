import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../entities/course";
import {environment} from "../../environments/environment.prod";
import {CourseModel} from "../model/courseModel";

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getCourse(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.apiServerUrl}/course/${courseId}`);
  }

  public getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiServerUrl}/course`);
  }

  public getMyCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiServerUrl}/course/my_courses`);
  }

  public getCoursesByStudent(studentId: number): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiServerUrl}/course/by_student/${studentId}`);
  }

  public addCourse(course: CourseModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/course`, course);
  }

  public makePublic(courseId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/course/make_public/${courseId}`);
  }

  public makePrivate(courseId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/course/make_private/${courseId}`);
  }

  public deleteCourse(courseId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/course/${courseId}`);
  }

  public updateCourse(courseId: number, course: CourseModel): Observable<void> {
    return this.http.put<void>(`${this.apiServerUrl}/course/${courseId}`, course);
  }

}
