import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MembersComponent} from "./members/members.component";

const routes: Routes = [
  {path: '', redirectTo: '/members', pathMatch: 'full'},
  {path: 'members', component: MembersComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
