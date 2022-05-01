import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class StudentMembershipService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addMemberToCourse(courseId : number, studentId : number): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/course/add_member_to_course/${courseId}`, studentId);
  }

  public removeMemberFromCourse(courseId : number, studentId : number): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/course/remove_member_from_course/${courseId}`, studentId);
  }

}
