import {Component, OnInit, EventEmitter, Input, Output, OnDestroy} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location }               from '@angular/common';
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/operator/switchMap';
import {Member} from "./../domain/member";
import {MemberService} from "../member.service";

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit, OnDestroy {
  member: Member;
  @Input()
  editMode = false;
  private sub: Subscription;

  constructor(
    private memberService: MemberService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.sub = this.route.params
      .switchMap((params: Params) => this.memberService.getMember(params['id']))
      .subscribe(member => this.member = member);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  edit() {
    this.editMode = true;
  }

  goBack(): void {
    this.location.back();
  }
}
