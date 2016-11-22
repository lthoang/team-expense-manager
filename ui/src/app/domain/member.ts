/**
 * Created by hoangtle on 11/21/2016.
 */
import {Name} from './name';
export class Member {
  id:string;
  name:Name;
  email:string;
  mobile:string;
  dob:Date;
  constructor(id:string,name:Name,email:string,mobile:string,dob:Date) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.mobile = mobile;
    this.dob = dob;
  }
}
