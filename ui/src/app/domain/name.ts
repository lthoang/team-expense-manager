/**
 * Created by hoangtle on 11/21/2016.
 */
export class Name {
  firstName:string;
  middleName:string;
  lastName:string;
  fullNameFML:string;
  constructor(firstName:string, middleName:string, lastName:string) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.fullNameFML = [firstName,middleName,lastName].join(' ');
  }
}
