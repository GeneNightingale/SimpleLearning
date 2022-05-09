import {Result} from "./result";

export interface TestResults {
  courseId?: number;
  name: string;
  marks: Result[];
}
