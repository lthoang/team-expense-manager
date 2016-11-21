import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Member} from "../domain/member";
import {MemberService} from "../member.service";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {
  errorMessage:string;
  members:Member[];
  selectedMember:Member;

  constructor(private memberService:MemberService) {
  }

  ngOnInit() {
    this.getMembers();
  }

  getMembers() {
    this.memberService.getMembers()
      .then(
        members => this.members = members,
        error => this.errorMessage = <any>error);
    console.log(JSON.stringify(this.members));
  }

  onSelect(member:Member):void {
    this.selectedMember = member;
  }
}
