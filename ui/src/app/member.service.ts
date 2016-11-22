import {Injectable} from '@angular/core';
import {Headers, Http, Response} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Member} from "./domain/member";

@Injectable()
export class MemberService {
  private headers = new Headers({'Content-Type': 'application/json'});
  private membersUrl = 'api/members';

  constructor(private http:Http) {
  }

  getMembers():Promise<Member[]> {
    return this.http.get(this.membersUrl)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getMember(id:string):Promise<Member> {
    const url = `${this.membersUrl}/${id}`;
    return this.http.get(url)
        .toPromise()
        .then(this.extractData)
        .catch(this.handleError);
  }

  //TODO: re-write this function
  // delete(id: string): Promise<void> {
  //   const url = `${this.membersUrl}`;
  //   return this.http.delete(url, {headers: this.headers})
  //     .toPromise()
  //     .then(() => null)
  //     .catch(this.handleError);
  // }

  //TODO: test this function
  create(member:Member): Promise<Member> {
    return this.http
      .post(this.membersUrl, JSON.stringify(member), {headers: this.headers})
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  update(member: Member): Promise<Member> {
    const url = `${this.membersUrl}/${member.id}`;
    return this.http
      .put(url, JSON.stringify(member), {headers: this.headers})
      .toPromise()
      .then(() => member)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body || {};
  }

  private handleError (error: Response | any) {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }

}
