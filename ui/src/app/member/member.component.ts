import { Component, OnInit, Input } from '@angular/core';
import {Member} from "./../domain/member";

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {
  @Input()
  member: Member;

  constructor() { }

  ngOnInit() {
  }

}
