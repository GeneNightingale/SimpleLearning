export class CourseModel {
  title: string;
  teacherId: number;
  description: string;

  constructor(title: string, teacherId: number, description: string) {
    this.title = title;
    this.teacherId = teacherId;
    this.description = description;
  }
}
