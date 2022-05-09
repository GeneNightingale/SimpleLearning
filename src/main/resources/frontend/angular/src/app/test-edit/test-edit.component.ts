import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../service/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DataSharingService} from "../service/datasharing.service";
import {AppearanceService} from "../service/appearance.service";
import {CoursesService} from "../service/courses.service";
import {UserService} from "../service/user.service";
import {Course} from "../entities/course";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Test} from "../entities/test";
import {TestModel} from "../model/testModel";
import {TestService} from "../service/test.service";
import {QuestionModel} from "../model/questionModel";
import {Answer} from "../entities/answer";

@Component({
  selector: 'app-test-edit',
  templateUrl: './test-edit.component.html',
  styleUrls: ['./test-edit.component.css']
})
export class TestEditComponent implements OnInit {

  public test: Test = {
    testId: 0,
    title: "default",
    courseId: 0,
    time: 0,
    questions: [],
    public: false,
    passed: false,
    grade: 0
  };

  formTest: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(60)]),
  });
  get title() {
    return this.formTest.get('title');
  }
  testModel: TestModel = new TestModel('', 0, 0);
  public courseTitle: string = 'default';

  public isInEditMode: boolean = false;

  private subscription: Subscription;

  isLoggedIn : boolean = false;

  isInAddAnswerMode: boolean = false;

  formAnswer: FormGroup = new FormGroup({
    answer: new FormControl('', [Validators.required, Validators.maxLength(120)]),
  });
  get answer() {
    return this.formAnswer.get('answer');
  }
  public answerModel : Answer = {
    answerId : 0,
    answer : ""
  }

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private activateRoute: ActivatedRoute,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService,
              private coursesService: CoursesService, private userService: UserService,
              private testService: TestService, private changeDetectorRef: ChangeDetectorRef) {
    this.subscription = activateRoute.params.subscribe(params=>this.test.testId=params['testId']);
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public currentQuestion : any = 1;

  public questionsModel: QuestionModel[] = [
    {
      questionId: 0,
      questionNum: 0,
      text: '',
      answer: '',
      answers: []
    }
  ]; //List of question models for the editor

  ngOnInit(): void {
    this.getTest();
    this.initializeUser();
  }

  initializeUser() : void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
    }
    if (!this.dataSharingService.isTeacher && this.dataSharingService.isStudent) {
      this.router.navigate(['/test/' + this.test.testId]);
    }
  }
  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  reloadPage() : void {
    window.location.reload();
  }

  //Test
  public getTest(): void {
    this.testService.getTest(this.test.testId).subscribe(
      (response: Test) => {
        this.test = response;
        if (this.test.title != null) {
          this.testModel.title = this.test.title;
        }
        if (this.test.time != null) {
          this.testModel.time = this.test.time/60;
        }
        if (this.test.courseId != null) {
          this.testModel.courseId = this.test.courseId;
          this.getCourse(this.test.courseId);
        }
        if (response.questions.length === 0)
          this.createEmptyQuestion();
        else {
          var questionId = response.questions[0].questionId;
          this.testService.getAnswersByQuestionId(questionId!).subscribe(
            (answers: Answer[]) => {
              if (answers.length === 0)
                this.createEmptyAnswers(questionId!, 0);
              else if (answers.length === 1)
                this.createEmptyAnswers(questionId!, 1);
              else {
                // @ts-ignore
                this.questionsModel = response.questions;
                var num : number = 0;
                this.questionsModel.forEach((item) => {
                  this.testService.getAnswersByQuestionId(item.questionId).subscribe(
                    (response: Answer[]) => {
                      item.answers = response;
                    }
                  );
                  item.questionNum = num + 1;
                  console.log(this.questionsModel[num]);
                  num++;
                });
              }
            }
          );
        }
        this.changeDetectorRef.detectChanges();
      }
    );
  }

  public getCourse(courseId : number): void {
    this.coursesService.getCourse(courseId).subscribe(
      (response: Course) => {
        this.courseTitle = response.title;
      }
    );
  }

  uneditTest() {
    (<HTMLInputElement>document.getElementById('title')).value = this.test.title;
    this.isInEditMode = false;
  }
  editTest() {
    this.isInEditMode = true;
  }

  submitTest() {
    const testTitle = (<HTMLInputElement>document.getElementById('title'));
    const testTime = (<HTMLInputElement>document.getElementById('time'));
    this.testService.updateTest(this.test.testId, testTitle.value, Number(testTime.value)).subscribe(
      (response: void) => {
        this.saveQuestion();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  createEmptyQuestion() {
    var emptyQuestion : QuestionModel = {
      questionId: 0,
      questionNum: 1,
      text: "Текст вопроса",
      answer: "Верный ответ",
      answers: []
    }
    this.testService.addQuestion(this.test.testId, emptyQuestion).subscribe(
      (response: void) => {
        this.getTest();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  createEmptyAnswers(questionId: number, amount: number) {
    var answer1 : Answer = {
      answerId: 0,
      answer: "Верный ответ"
    }
    var answer2 : Answer = {
      answerId: 0,
      answer: "Неверный ответ"
    }
    if (amount === 0)
      this.addAnswer(questionId, answer1);
    else
      this.addAnswer(questionId, answer2);
  }

  addAnswer(questionId: number, answer: Answer) : void{
    this.testService.addAnswer(questionId, answer).subscribe(
      (response: void) => {
        this.getTest();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      },
    );
  }

  pickedOption(event : any) : void {
    this.testService.updateQuestion(this.questionsModel[this.currentQuestion-1]).subscribe(
      (response: void) => {
        this.currentQuestion = event.value;
        this.getTest();
        this.unaddAnswerMode();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteCurrentPage() {
    if(confirm("Точно удалить текущий вопрос?")) {
      this.testService.deleteQuestion(this.questionsModel[this.currentQuestion - 1].questionId).subscribe(
        (response: void) => {
          this.currentQuestion--;
          this.getTest();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  addQuestion() {
    var question : QuestionModel = {
      questionId: 0,
      questionNum: this.questionsModel.length+1,
      text: "Текст Вопроса",
      answer: "",
      answers: []
    }
    this.testService.addQuestion(this.test.testId, question).subscribe(
      (response: void) => {
        this.getTest();
        this.currentQuestion = question.questionNum;
        this.changeDetectorRef.detectChanges();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  nextQuestion() {
    this.testService.updateQuestion(this.questionsModel[this.currentQuestion-1]).subscribe(
      (response: void) => {
        this.currentQuestion++;
        this.getTest();
        this.unaddAnswerMode();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  prevQuestion() {
    this.testService.updateQuestion(this.questionsModel[this.currentQuestion-1]).subscribe(
      (response: void) => {
        this.currentQuestion--;
        this.getTest();
        this.unaddAnswerMode();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  saveQuestion() {
    this.testService.updateQuestion(this.questionsModel[this.currentQuestion-1]).subscribe(
      (response: void) => {
        this.uneditTest();
        this.getTest();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  pickedAnswer(event : any) : void {
    this.questionsModel[this.currentQuestion-1].answer = event.value;
  }

  deleteAnswer(answerId: any) {
    if(confirm("Точно удалить этот ответ?")) {
      this.testService.deleteAnswer(answerId).subscribe(
        (response: void) => {
          this.getTest();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  addAnswerToQuestion() {
    this.testService.addAnswer(this.questionsModel[this.currentQuestion-1].questionId, this.answerModel).subscribe(
      (response: void) => {
        this.getTest();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      },
    );
  }

  addAnswerMode() {
    this.isInAddAnswerMode = true;
  }
  unaddAnswerMode() {
    this.isInAddAnswerMode = false;
  }
}
