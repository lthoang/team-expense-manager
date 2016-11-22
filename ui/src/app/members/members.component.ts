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
  members:Member[];
  selectedMember:Member;
  addNewMemberMode = false;

  constructor(
    private memberService:MemberService,
    private router:Router) {
  }

  ngOnInit() {
    this.getMembers();
  }

  getMembers() {
    this.memberService.getMembers()
      .then(members => this.members = members);
  }

  onSelect(member:Member):void {
    this.selectedMember = member;
  }

  gotoDetail(): void {
    this.router.navigate(['/members', this.selectedMember.id]);
  }

  addNewMember() {
    this.addNewMemberMode = true;
  }
}