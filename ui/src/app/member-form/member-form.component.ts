import {Component, OnInit, Input} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';

import {Member} from "../domain/member";
import {Name} from "../domain/name";
import {MemberService} from "../member.service";

@Component({
  selector: 'app-member-form',
  templateUrl: './member-form.component.html',
  styleUrls: ['./member-form.component.css']
})
export class MemberFormComponent implements OnInit {
  @Input()
  member = new Member(null, new Name('', '', ''), '', '', new Date());
  active = true;
  memberForm: FormGroup;

  membersUrl = '/members';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private memberService: MemberService) {
  }

  onSubmit() {
    this.member = this.formToMember(this.memberForm.value);
    if (this.member.id != null) {
      this.memberService.update(this.member)
        .then(() => this.navigateBackToRoot());
    } else {
      this.memberService.create(this.member)
        .then(() => this.navigateBackToRoot());
    }
  }
  private formToMember(value: any): Member {
    return new Member(
      value.id,
      new Name(value.firstName, value.middleName, value.lastName),
      value.email,
      value.mobile,
      value.dob
    )
  }
  navigateBackToRoot(): void {
    this.router.navigate([this.membersUrl]);
  }

  ngOnInit() {
    this.buildForm();
  }

  buildForm(): void {
    console.log('something');
    this.memberForm = this.formBuilder.group({
      'id': [this.member.id],
      'firstName': [this.member.name.firstName, [
        Validators.required,
        Validators.maxLength(24)
      ]
      ],
      'middleName': [this.member.name.middleName],
      'lastName': [this.member.name.lastName, [
        Validators.required,
        Validators.maxLength(24)
      ]
      ],
      'email': [this.member.email, Validators.required],
      'mobile': [this.member.mobile, Validators.required],
      'dob': [this.member.dob, Validators.required]
    });

    this.memberForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

    this.onValueChanged(); // (re)set validation messages now
  }


  onValueChanged(data?: any) {
    if (!this.memberForm) {
      return;
    }
    const form = this.memberForm;

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
    'firstName': '',
    'lastName': '',
    'email': '',
    'mobile': '',
    'dob': ''
  };

  validationMessages = {
    'firstName': {
      'required': 'First name is required.',
      'maxlength': 'First name cannot be more than 24 characters long.'
    },
    'lastName': {
      'required': 'Last name is required.',
      'maxlength': 'Last name cannot be more than 24 characters long.'
    },
    'email': {
      'required': 'Email is required.'
    },
    'mobile': {
      'required': 'Mobile is required.'
    },
    'dob': {
      'required': 'Date of birth is required.'
    }
  };
}
