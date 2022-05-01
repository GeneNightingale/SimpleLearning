import {Answer} from "./answer";

export interface Question {
  questionId?: number;
  questionNum?: number;
  text?: string;
  answer?: string;
  answers?: Answer[];
}
