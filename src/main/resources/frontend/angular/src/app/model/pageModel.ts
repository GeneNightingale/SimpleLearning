export class PageModel {
  pageId: number;
  text: string;
  pageNum: number;

  constructor(text: string, pageId: number, number: number) {
    this.text = text;
    this.pageId = pageId;
    this.pageNum = number;
  }
}
