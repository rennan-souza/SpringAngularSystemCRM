import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  tokenData: any;
  progressBar: boolean = false;
  totalElements = 0;
  page = 0;
  size = 5;
  users: User[] = []
  searchUser: string = '';

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private authService: AuthService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.tokenData = this.authService.getTokenData();
    this.findAll();
  }

  findAll() {
    this.progressBar = true;
    this.userService.findAll(this.page, this.size, this.searchUser).subscribe(response => {
      response.content ? this.users = response.content : ''
      this.totalElements = response.totalElements;
      this.page = response.number;
      this.progressBar = false;
    }, errorReponse => {
      this.progressBar = false
    }) 
  }

  search() {
    this.findAll();
  }

  paginate(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize
    this.findAll(); 
  }

  formatRole(value: string) {
    return this.roleService.formatRole(value);
  }

}
