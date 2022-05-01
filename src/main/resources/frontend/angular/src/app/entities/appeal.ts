import {Answer} from "./answer";
import {User} from "./user";

export interface Appeal {
  appealId?: number;
  user?: User;
  status?: string;
  text?: string;
  reasonForDenial?: string;
  answers?: Answer[];
}
