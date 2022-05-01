import {Question} from "./question";

export interface Test {
  testId: number;
  title: string;
  courseId: number;
  time: number;
  questions: Question[];
  public: boolean;
  passed: boolean;
  grade: number;
}
