import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.prod";
import {Test} from "../entities/test";
import {Answer} from "../entities/answer";
import {TestModel} from "../model/testModel";
import {QuestionModel} from "../model/questionModel";
import {TestResultModel} from "../model/testResultModel";
import {Mark} from "../entities/mark";
import {Result} from "../entities/result";

@Injectable({
  providedIn: 'root'
})
export class TestService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getTest(testId: number): Observable<Test> {
    return this.http.get<Test>(`${this.apiServerUrl}/test/${testId}`);
  }

  public getAllTests(): Observable<Test[]> {
    return this.http.get<Test[]>(`${this.apiServerUrl}/test`);
  }

  public getTestsByCourseId(courseId : number): Observable<Test[]> {
    return this.http.get<Test[]>(`${this.apiServerUrl}/test/by_course/${courseId}`);
  }

  public addTest(testModel: TestModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/test/by_course/${testModel.courseId}`, testModel);
  }

  public deleteTest(testId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/test/${testId}`);
  }

  public updateTest(testId: number, title: string, time: number): Observable<void> {
    var testModel : TestModel = new TestModel('', 0, 0);
    testModel.title = title;
    testModel.time = time*60;
    return this.http.put<void>(`${this.apiServerUrl}/test/${testId}`, testModel);
  }

  public makePublic(testId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/test/make_public/${testId}`);
  }

  public makePrivate(testId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/test/make_private/${testId}`);
  }

  public addQuestion(testId: number, questionModel: QuestionModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/question/by_test/${testId}`, questionModel);
  }

  public updateQuestion(questionModel: QuestionModel): Observable<void> {
    return this.http.put<void>(`${this.apiServerUrl}/question/${questionModel.questionId}`, questionModel);
  }

  public deleteQuestion(questionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/question/${questionId}`);
  }

  public getAnswersByQuestionId(questionId : number): Observable<Answer[]> {
    return this.http.get<Answer[]>(`${this.apiServerUrl}/answer/by_question/${questionId}`);
  }

  public addAnswer(questionId: number, answer: Answer): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/answer/by_question/${questionId}`, answer);
  }

  public deleteAnswer(answerId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/answer/${answerId}`);
  }

  public completeTest(testResultModel: TestResultModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/test/complete_test/${testResultModel.testId}`, testResultModel);
  }

  public getResultsByTestId(testId: number): Observable<Result[]> {
    return this.http.get<Result[]>(`${this.apiServerUrl}/result/by_test/${testId}`);
  }

  public getMyResultsByCourseId(courseId: number, studentId: number): Observable<Result[]> {
    return this.http.get<Result[]>(`${this.apiServerUrl}/result/by_course/${courseId}/${studentId}`);
  }

}
