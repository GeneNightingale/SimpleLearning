import {Material} from "./material";
import {Lecture} from "./lecture";
import {Test} from "./test";
import {Appeal} from "./appeal";

export interface Course {
  courseId: number;
  title: string;
  teacherId: number;
  description?: string;
  materials?: Material[];
  lectures?: Lecture[];
  tests?: Test[];
  appeals?: Appeal[];
  public?: boolean;
}
