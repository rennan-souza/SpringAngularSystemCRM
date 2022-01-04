import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { filter } from 'rxjs';
import { UserForgotPassword } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPassword: UserForgotPassword = new UserForgotPassword();
  loader: boolean = false;

  constructor(
    private service: UserService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  submit() {
    this.loader = true
    this.service.forgotPassword(this.forgotPassword).subscribe((response) => {
      this.router.navigate(['/criar-nova-senha'])
    }, errorResponse => {
      this.loader = false
      this.router.navigate(['/criar-nova-senha'])
    });
  }
}
