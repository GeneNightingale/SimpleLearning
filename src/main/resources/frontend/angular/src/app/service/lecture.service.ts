import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.prod";
import {Lecture} from "../entities/lecture";
import {LectureModel} from "../model/lectureModel";
import {Page} from "../entities/page";

@Injectable({
  providedIn: 'root'
})
export class LectureService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getLecture(lectureId: number): Observable<Lecture> {
    return this.http.get<Lecture>(`${this.apiServerUrl}/lecture/${lectureId}`);
  }

  public getAllLectures(): Observable<Lecture[]> {
    return this.http.get<Lecture[]>(`${this.apiServerUrl}/lecture`);
  }

  public addLecture(lectureModel: LectureModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/lecture/by_course/${lectureModel.courseId}`, lectureModel);
  }

  public deleteLecture(lectureId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/lecture/${lectureId}`);
  }

  public makePublic(lectureId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/lecture/make_public/${lectureId}`);
  }

  public makePrivate(lectureId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/lecture/make_private/${lectureId}`);
  }

  public updateLecture(lectureId: number, lectureModel: LectureModel): Observable<void> {
    return this.http.put<void>(`${this.apiServerUrl}/lecture/${lectureId}`, lectureModel);
  }

  public addEmptyPage(lectureId: number, page: Page): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/page/by_lecture/${lectureId}`, page);
  }

  public updatePage(page: Page): Observable<void> {
    return this.http.put<void>(`${this.apiServerUrl}/page/${page.pageId}`, page);
  }

  public deletePage(pageId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/page/${pageId}`);
  }

}
