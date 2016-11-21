/**
 * Created by trhoanglee on 11/22/16.
 */
import {Member} from './member';
export class Team {
  id:string;
  name:string;
  description:string;
  createdDate:Date;
  manager:Member;
}
