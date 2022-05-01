import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../entities/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/${userId}`)
  }

  public getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/current_user`)
  }

  public getMemberStudents(courseId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/course/students_by_course_id/${courseId}`);
  }

  public getNotMemberStudents(courseId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/course/not_students_by_course_id/${courseId}`);
  }
}
