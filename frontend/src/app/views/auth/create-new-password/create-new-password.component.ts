import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserCreateNewPassword } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-new-password',
  templateUrl: './create-new-password.component.html',
  styleUrls: ['./create-new-password.component.scss']
})
export class CreateNewPasswordComponent implements OnInit {

  userCreateNewPassword: UserCreateNewPassword = new UserCreateNewPassword();
  loader: boolean = false;

  constructor(
    private service: UserService,
    private toastr: ToastrService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  submit() {
    this.service.createNewPassword(this.userCreateNewPassword).subscribe((response) => {
      this.toastr.success("Senha alterada com sucesso", "Sucesso");
      this.router.navigate(['/login'])
    }, errorResponse => {
      this.toastr.error(errorResponse.error.message, "Erro");
    })
  }
}
