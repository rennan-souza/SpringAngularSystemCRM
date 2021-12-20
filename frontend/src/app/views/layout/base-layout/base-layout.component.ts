import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.scss']
})
export class BaseLayoutComponent implements OnInit {

  tokenData: any;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.tokenData = this.authService.getTokenData();
  }

  sidebarExpand() {
    document.getElementById('sidebarBg')?.classList.toggle('sidebar-bg-expand');
    document.getElementById('sidebar')?.classList.toggle('sidebar-expand');
    document.getElementById('content')?.classList.toggle('content-expand');
  }

  closeSidebar() {
    document.getElementById('sidebarBg')?.classList.remove('sidebar-bg-expand');
    document.getElementById('sidebar')?.classList.remove('sidebar-expand');
    document.getElementById('content')?.classList.remove('content-expand');
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login'])
  }

  hasAnyRoles() {
    return this.authService.getUserRoles();
  }
}
