import { Component, OnInit, Input } from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import {Team} from '../domain/team';
import {TeamService} from "../team.service";
import {Member} from "../domain/member";
import {MemberService} from "../member.service";
import {Name} from "../domain/name";

@Component({
  selector: 'app-team-form',
  templateUrl: './team-form.component.html',
  styleUrls: ['./team-form.component.css'],
  providers: [MemberService]
})
export class TeamFormComponent implements OnInit {
  @Input()
  team = new Team(null,'','',new Date(),new Member(null, new Name('','',''),'','',new Date()));
  @Input()
  createTeamMode = true;
  teamForm: FormGroup;
  managers: Observable<Member[]>;
  private searchTerms = new Subject<string>();

  teamsUrl = '/teams';
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private teamService: TeamService,
    private memberService: MemberService
  ) { }

  ngOnInit() {
    this.buildForm();

    this.managers = this.searchTerms
      .debounceTime(300)        // wait for 300ms pause in events
      .distinctUntilChanged()   // ignore if next search term is same as previous
      .switchMap(term => term   // switch to new observable each time
        // return the http search observable
        ? this.memberService.search(term)
        : Observable.of<Member[]>([]))
      .catch(this.handleError);
  }

  searchManager(term: string): void {
    this.searchTerms.next(term);
  }

  setManager(manager: Member) {
    this.team = this.formToTeam(this.teamForm.value);
    this.team.manager = manager;
    this.searchTerms.next(null);
    this.buildForm();
  }

  onSubmit() {
    this.team = this.formToTeam(this.teamForm.value);
    if (this.team.id != null) {
      this.teamService.update(this.team)
        .then(() => this.navigateBackToRoot());
    } else {
      this.teamService.create(this.team)
        .then(() => this.navigateBackToRoot());
    }
  }

  navigateBackToRoot(): void {
    this.createTeamMode = false;
    this.router.navigate([this.teamsUrl]);
  }

  private formToTeam(value: any) : Team {
    return new Team(
      value.id,
      value.name,
      value.description,
      value.createdDate,
      value.manager
    )
  }

  buildForm(): void {
    this.teamForm = this.formBuilder.group({
      'id': [this.team.id],
      'name': [this.team.name, [
        Validators.required,
        Validators.maxLength(100)
      ]
      ],
      'description': [this.team.description, Validators.maxLength(1000)],
      'createdDate': [this.team.createdDate, Validators.required],
      'manager': [this.team.manager, Validators.required],
      'managerName': [this.team.manager.name.fullNameFML, Validators.required]
    });

    this.teamForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

    this.onValueChanged(); // (re)set validation messages now
  }

  onValueChanged(data?: any) {
    if (!this.teamForm) {
      return;
    }
    const form = this.teamForm;

    for (const field in this.formErrors) {
      // clear previous error message (if any)
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.dirty && !control.valid) {
        const messages = this.validationMessages[field];
        for (const key in control.errors) {
          this.formErrors[field] += messages[key] + ' ';
        }
      }
    }
  }

  formErrors = {
    'name': '',
    'description': '',
    'createdDate': '',
    'manager': '',
    'managerName':''
  };

  validationMessages = {
    'name': {
      'required': 'Team name is required.',
      'maxlength': 'Team name cannot be more than 100 characters long.'
    },
    'description': {
      'maxlength': 'Last name cannot be more than 1000 characters long.'
    },
    'createdDate': {
      'required': 'Founded date is required.'
    },
    'manager': {
      'required': 'Manager is required.'
    },
    'managerName': {
      'required': 'Manager is required.'
    }
  };

  private handleError (error: Response | any) {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}
