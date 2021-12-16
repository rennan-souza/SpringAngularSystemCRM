import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { LoginComponent } from './views/auth/login/login.component';
import { DashboardComponent } from './views/home/dashboard/dashboard.component';
import { BaseLayoutComponent } from './views/layout/base-layout/base-layout.component';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { UserRegisterComponent } from './views/user/user-register/user-register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '', component: BaseLayoutComponent, children: [
      { path: '', component: DashboardComponent, canActivate: [ AuthGuard ] },
      { path: 'usuarios', component: UserListComponent, canActivate: [ AuthGuard, RoleGuard  ], data: { role: 'ROLE_ADMIN'} },
      { path: 'usuarios/cadastrar', component: UserRegisterComponent, canActivate: [ AuthGuard, RoleGuard  ], data: { role: 'ROLE_ADMIN'} },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
