import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location }               from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {Member} from "./../domain/member";
import {MemberService} from "../member.service";

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {
  @Input()
  member: Member;

  constructor(
    private memberService: MemberService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.params
      .switchMap((params: Params) => this.memberService.getMember(params['id']))
      .subscribe(member => this.member = member);
  }

  save(): void {
    this.memberService.update(this.member)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
