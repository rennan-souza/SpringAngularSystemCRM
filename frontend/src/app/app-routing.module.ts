import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { CreateNewPasswordComponent } from './views/auth/create-new-password/create-new-password.component';
import { ForgotPasswordComponent } from './views/auth/forgot-password/forgot-password.component';
import { LoginComponent } from './views/auth/login/login.component';
import { CustomerDeleteComponent } from './views/customer/customer-delete/customer-delete.component';
import { CustomerEditComponent } from './views/customer/customer-edit/customer-edit.component';
import { CustomerListComponent } from './views/customer/customer-list/customer-list.component';
import { CustomerRegisterComponent } from './views/customer/customer-register/customer-register.component';
import { NotFoundComponent } from './views/error/not-found/not-found.component';
import { DashboardComponent } from './views/home/dashboard/dashboard.component';
import { BaseLayoutComponent } from './views/layout/base-layout/base-layout.component';
import { ProductDeleteComponent } from './views/product/product-delete/product-delete.component';
import { ProductEditComponent } from './views/product/product-edit/product-edit.component';
import { ProductListComponent } from './views/product/product-list/product-list.component';
import { ProductRegisterComponent } from './views/product/product-register/product-register.component';
import { ProfileComponent } from './views/profile/profile.component';
import { SecurityComponent } from './views/security/security.component';
import { UserDeleteComponent } from './views/user/user-delete/user-delete.component';
import { UserEditComponent } from './views/user/user-edit/user-edit.component';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { UserRegisterComponent } from './views/user/user-register/user-register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'esqueci-minha-senha', component: ForgotPasswordComponent },
  { path: 'criar-nova-senha', component: CreateNewPasswordComponent },
  {
    path: '', component: BaseLayoutComponent, children: [
      { path: '', component: DashboardComponent, canActivate: [ AuthGuard ] },

      { path: 'usuarios', component: UserListComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_ADMIN'} },
      { path: 'usuarios/cadastrar', component: UserRegisterComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_ADMIN'} },
      { path: 'usuarios/editar/:id', component: UserEditComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_ADMIN'} },
      { path: 'usuarios/excluir/:id', component: UserDeleteComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_ADMIN'} },

      { path: 'clientes', component: CustomerListComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_OPERATOR'} },
      { path: 'clientes/cadastrar', component: CustomerRegisterComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_OPERATOR'} },
      { path: 'clientes/editar/:id', component: CustomerEditComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_OPERATOR'} },
      { path: 'clientes/excluir/:id', component: CustomerDeleteComponent, canActivate: [ AuthGuard, RoleGuard ], data: { role: 'ROLE_OPERATOR'} },

      { path: 'produtos', component: ProductListComponent, canActivate: [ AuthGuard ] },
      { path: 'produtos/cadastrar', component: ProductRegisterComponent, canActivate: [ AuthGuard ] },
      { path: 'produtos/editar/:id', component: ProductEditComponent, canActivate: [ AuthGuard ] },
      { path: 'produtos/excluir/:id', component: ProductDeleteComponent, canActivate: [ AuthGuard ] },

      { path: 'profile', component: ProfileComponent, canActivate: [ AuthGuard ] },

      { path: 'seguranca', component: SecurityComponent, canActivate: [ AuthGuard ] },
    ]
  },

  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '/404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
