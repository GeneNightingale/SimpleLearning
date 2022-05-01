import {Page} from "./page";

export interface Lecture {
  lectureId: number;
  title: string;
  pages: Page[];
  courseId: number;
  public: boolean;
}
