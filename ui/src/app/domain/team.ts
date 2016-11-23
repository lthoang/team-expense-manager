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
  totalFund:number;
  totalExpense:number;
  constructor(id:string,name:string,description:string,createdDate:Date,manager:Member) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
    this.manager = manager;
  }
}
