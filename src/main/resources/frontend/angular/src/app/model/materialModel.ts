export class MaterialModel {
  title: string;
  courseId: number;
  link: string;

  constructor(title: string, courseId: number, link: string) {
    this.title = title;
    this.courseId = courseId;
    this.link = link;
  }
}
