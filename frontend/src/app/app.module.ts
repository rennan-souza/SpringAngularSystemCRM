import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule } from 'ngx-mask';

import { LoginComponent } from './views/auth/login/login.component';
import { DashboardComponent } from './views/home/dashboard/dashboard.component';
import { BaseLayoutComponent } from './views/layout/base-layout/base-layout.component';
import { UserListComponent } from './views/user/user-list/user-list.component';
import { UserRegisterComponent } from './views/user/user-register/user-register.component';
import { getPortuguesePaginatorIntl } from 'src/utils/pagination-pt-br';
import { UserEditComponent } from './views/user/user-edit/user-edit.component';
import { UserDeleteComponent } from './views/user/user-delete/user-delete.component';
import { CustomerListComponent } from './views/customer/customer-list/customer-list.component';
import { CustomerRegisterComponent } from './views/customer/customer-register/customer-register.component';
import { CustomerEditComponent } from './views/customer/customer-edit/customer-edit.component';
import { CustomerDeleteComponent } from './views/customer/customer-delete/customer-delete.component';
import { ProductListComponent } from './views/product/product-list/product-list.component';
import { ProductRegisterComponent } from './views/product/product-register/product-register.component';
import { ProductEditComponent } from './views/product/product-edit/product-edit.component';
import { ProductDeleteComponent } from './views/product/product-delete/product-delete.component';
import { ProfileComponent } from './views/profile/profile.component';
import { SecurityComponent } from './views/security/security.component';
import { ForgotPasswordComponent } from './views/auth/forgot-password/forgot-password.component';
import { CreateNewPasswordComponent } from './views/auth/create-new-password/create-new-password.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    BaseLayoutComponent,
    UserListComponent,
    UserRegisterComponent,
    UserEditComponent,
    UserDeleteComponent,
    CustomerListComponent,
    CustomerRegisterComponent,
    CustomerEditComponent,
    CustomerDeleteComponent,
    ProductListComponent,
    ProductRegisterComponent,
    ProductEditComponent,
    ProductDeleteComponent,
    ProfileComponent,
    SecurityComponent,
    ForgotPasswordComponent,
    CreateNewPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatSelectModule,
    MatDialogModule,
    NgxMaskModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 4000,
      closeButton: true,
      progressBar: true,
      positionClass: "toast-bottom-right"
    }),
  ],
  providers: [
    {
      provide: MatPaginatorIntl,
      useValue: getPortuguesePaginatorIntl()
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
