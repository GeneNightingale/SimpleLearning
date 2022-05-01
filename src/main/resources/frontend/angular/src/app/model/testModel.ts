export class TestModel {
  title: string;
  courseId: number;
  time: number;

  constructor(title: string, courseId: number, time: number) {
    this.title = title;
    this.courseId = courseId;
    this.time = time;
  }
}
