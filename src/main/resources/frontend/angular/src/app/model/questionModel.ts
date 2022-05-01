import {Answer} from "../entities/answer";

export class QuestionModel {
  questionId: number;
  questionNum: number;
  text: string;
  answer: string;
  answers: Answer[];

  constructor(text: string, questionId: number, questionNum: number, answer: string, answers: Answer[]) {
    this.questionId = questionId;
    this.questionNum = questionNum;
    this.text = text;
    this.answer = answer;
    this.answers = answers;
  }
}
