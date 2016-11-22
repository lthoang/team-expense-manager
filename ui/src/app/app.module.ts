import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { MemberComponent } from './member/member.component';
import { MembersComponent } from './members/members.component';
import {MemberService} from "./member.service";
import {AppRoutingModule} from "./app-routing.module";
import { TeamsComponent } from './teams/teams.component';
import { TeamComponent } from './team/team.component';
import { MemberFormComponent } from './member-form/member-form.component';

@NgModule({
  declarations: [
    AppComponent,
    MemberComponent,
    MembersComponent,
    TeamsComponent,
    TeamComponent,
    MemberFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [MemberService],
  bootstrap: [AppComponent]
})
export class AppModule { }
