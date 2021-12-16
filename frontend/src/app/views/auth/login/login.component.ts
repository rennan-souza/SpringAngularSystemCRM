import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserSigninData } from 'src/app/models/userSigninData';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  userSigninData: UserSigninData;
  loader: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService,
  ) { 
    this.userSigninData = new UserSigninData('', '');
  }

  ngOnInit(): void {
  }

  signin() {
    this.loader = true;
    this.authService.signin(this.userSigninData).subscribe(response => {
      const access_token = JSON.stringify(response);
      localStorage.setItem('token', access_token);
      this.router.navigate(['/']);
    }, errorResponse => {
      this.loader = false;
      this.toast.error("Usuário ou senha inválidos", "Erro");
    });
  }

}
