import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Role } from 'src/app/models/role';
import { User } from 'src/app/models/user';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  user: User;
  id: number = 0;
  roles: Role[] = [];
  loader: boolean = false;

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) { 
    this.user = new User();
  }

  ngOnInit(): void {
    this.findAllRoles();
    let params: Observable<Params> = this.activatedRoute.params
    params.subscribe(urlParams => {
      this.id = urlParams['id'];
      if (this.id) {
        this.userService.findById(this.id).subscribe(response => {
          this.user = response;
        }, errorResponse => {
          errorResponse = this.router.navigate(['/usuarios']);
        })
      }
    })
  }

  compareUserRoleAndRole(r: Role, ur: User): boolean {
    return r && ur ? r.id === ur.id : r === ur;
  }

  findAllRoles() {
    this.roleService.findAll().subscribe(response => {
      this.roles = response
    })
  }

  update() {
    this.loader = true
    this.userService.update(this.user).subscribe(response => {
      this.toastr.success("Usuário editado", "Sucesso");
      this.router.navigate(['/usuarios'])
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }

  formatRole(value: string) {
    return this.roleService.formatRole(value);
  }

}
