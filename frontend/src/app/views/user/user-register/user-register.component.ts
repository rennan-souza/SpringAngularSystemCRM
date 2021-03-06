import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Role } from 'src/app/models/role';
import { User } from 'src/app/models/user';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent implements OnInit {

  user: User;
  roles: Role[] = [];
  loader: boolean = false;

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private toastr: ToastrService,
    private router: Router,
  ) { 
    this.user = new User();
  }

  ngOnInit(): void {
    this.findAllRoles();
  }

  findAllRoles() {
    this.roleService.findAll().subscribe(response => {
      this.roles = response
    })
  }

  save() {
    this.loader = true
    this.userService.save(this.user).subscribe(() => {
      this.toastr.success("Usuário cadastrado.", "Sucesso");
      this.router.navigate(['/usuarios']);
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }

  formatRole(value: string) {
    return this.roleService.formatRole(value);
  }
}
