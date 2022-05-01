import {QuestionModel} from "./questionModel";

export class TestResultModel {
  testId: number;
  answers: QuestionModel[];

  constructor(testId: number, questions: QuestionModel[]) {
    this.testId = testId;
    this.answers = questions;
  }
}
