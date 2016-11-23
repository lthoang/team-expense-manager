import { Component, OnInit, Input } from '@angular/core';
import {Router} from "@angular/router";
import {Team} from "../domain/team";
import {TeamService} from "../team.service";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {
  teams:Team[];
  selectedTeam:Team;
  @Input()
  createTeamMode = false;
  constructor(
    private teamService:TeamService,
    private router:Router
  ) { }

  ngOnInit() {
    console.log('on  init teams component');
    this.getTeams();
  }

  getTeams() {
    this.teamService.getTeams()
      .then(teams => this.teams = teams);
  }

  onSelect(team:Team):void {
    this.selectedTeam = team;
  }

  createTeam() {
    this.createTeamMode = true;
  }

  cancelCreateTeam() {
    this.createTeamMode = false;
  }
}
