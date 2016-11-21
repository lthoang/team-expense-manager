import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MembersComponent} from "./members/members.component";
import {MemberComponent} from "./member/member.component";

const routes: Routes = [
  {path: '', redirectTo: '/members', pathMatch: 'full'},
  {path: 'members', component: MembersComponent},
  {path: 'members/:id', component: MemberComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
