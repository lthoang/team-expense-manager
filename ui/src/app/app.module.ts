import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { MemberComponent } from './member/member.component';
import { MembersComponent } from './members/members.component';
import {MemberService} from "./member.service";
import {AppRoutingModule} from "./app-routing.module";
import { TeamsComponent } from './teams/teams.component';
import { TeamComponent } from './team/team.component';

@NgModule({
  declarations: [
    AppComponent,
    MemberComponent,
    MembersComponent,
    TeamsComponent,
    TeamComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [MemberService],
  bootstrap: [AppComponent]
})
export class AppModule { }
