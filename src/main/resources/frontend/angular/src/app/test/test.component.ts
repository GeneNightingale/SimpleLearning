import {ChangeDetectorRef, Component, HostListener, OnInit, Pipe, PipeTransform} from '@angular/core';
import {timer, Subscription} from "rxjs";
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
import {TestService} from "../service/test.service";
import {QuestionModel} from "../model/questionModel";
import {Answer} from "../entities/answer";
import {TestResultModel} from "../model/testResultModel";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

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

  public courseTitle: string = 'default';

  private subscription: Subscription;

  // @ts-ignore
  private countDown: Subscription = undefined;

  public counter: number = 1000;

  isLoggedIn : boolean = false;

  completed: boolean = false;

  formAnswer: FormGroup = new FormGroup({
    answer: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(120)]),
  });
  get answer() {
    return this.formAnswer.get('answer');
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

  public leftButtonText = "Левая кнопка";
  public rightButtonText = "Правая кнопка";

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
        if (this.test.courseId != null) {
          this.getCourse(this.test.courseId);
        }
        var questionId = response.questions[0].questionId;
        this.testService.getAnswersByQuestionId(questionId!).subscribe(
          (answers: Answer[]) => {
            // @ts-ignore
            this.questionsModel = response.questions;
            var num : number = 0;
            this.questionsModel.forEach((item) => {
              this.testService.getAnswersByQuestionId(item.questionId).subscribe(
                (response: Answer[]) => {
                  item.answers = response;
                });
              item.questionNum = num + 1;
              console.log(this.questionsModel[num]);
              num++;
            });
            if (this.test.passed) {
              this.completed = true;
              this.router.navigate(['/course/' + this.test.courseId]);
            }
            this.initializeButtons();
            this.counter = this.test.time;
            this.countDown = timer(0, 1000).subscribe(() => {
              --this.counter;
              if (this.counter === 0)
                this.completeTest();
            });

            this.changeDetectorRef.detectChanges();
          });
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

  pickedOption(event : any) : void {
    this.currentQuestion = event.value;
    this.initializeButtons();
    this.changeDetectorRef.detectChanges();
  }

  rightButton() {
    if (this.currentQuestion < this.questionsModel.length)
      this.currentQuestion++;
    else
      if (confirm("Закончить тест?"))
        this.completeTest();
    this.initializeButtons();
  }

  leftButton() {
    if (this.currentQuestion > 1)
      this.currentQuestion--;
    else
      if (confirm("Закончить тест?"))
        this.completeTest();
    this.initializeButtons();
  }

  public initializeButtons() {
    console.log("Current question:" + this.currentQuestion + ", Length:" + this.questionsModel.length);
    if (this.currentQuestion < 2) {
      this.leftButtonText = "Назад к курсу"
    } else {
      this.leftButtonText = "Предыдущий вопрос"
    }

    if (this.currentQuestion == this.questionsModel.length) {
      this.rightButtonText = "Завершить тест"
    } else {
      this.rightButtonText = "Следующий вопрос"
    }
  }

  pickedAnswer(event : any) : void {
    this.questionsModel[this.currentQuestion-1].answer = event.value;
  }

  completeTest() : void {
    var testResults : TestResultModel = {answers: [], testId: 0};
    testResults.testId = this.test.testId;
    testResults.answers = this.questionsModel;
    this.testService.completeTest(testResults).subscribe(
      (response: void) => {
        this.completed = true;
        this.router.navigate(['/course/' + this.test.courseId]);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  @HostListener('window:beforeunload', ['$event'])
  async ngOnDestroy() {
    if(!this.completed)
      this.completeTest();
  }

  openCourse(courseId: number) {
    if (confirm("Закончить тест?"))
      this.completeTest();
  }

}

@Pipe({
  name: "formatTime"
})
export class FormatTimePipe implements PipeTransform {
  transform(value: number): string {
    const minutes: number = Math.floor(value / 60);
    return (
      ("00" + minutes).slice(-2) +
      ":" +
      ("00" + Math.floor(value - minutes * 60)).slice(-2)
    );
  }
}
