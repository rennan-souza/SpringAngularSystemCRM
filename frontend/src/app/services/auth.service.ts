import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserSigninData } from '../models/userSigninData';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  urlBaseSignin: string = environment.urlBaseSignin;
  apiBaseUrl: string = environment.apiUrlBase;
  clientId: string = environment.clientId;
  clientSecret: string = environment.clientSecret;
  jwtHelper: JwtHelperService = new JwtHelperService;

  constructor(
    private http: HttpClient
  ) { }

  signin(userSigninData: UserSigninData): Observable<UserSigninData> {
    const params = new HttpParams()
      .set('username', userSigninData.username)
      .set('password', userSigninData.password)
      .set('grant_type', 'password');

    const headers = {
      'Authorization': 'Basic ' + btoa(`${this.clientId}:${this.clientSecret}`),
      'Content-Type': 'application/x-www-form-urlencoded'
    }
    return this.http.post<UserSigninData>(this.urlBaseSignin, params.toString(), { headers });
  }

  isAuthenticated(): boolean {
    const token = this.getTokenLocalstorage();
    if (token) {
      const expired = this.jwtHelper.isTokenExpired(token);
      return !expired
    }
    return false;
  }

  getTokenLocalstorage() {
    const tokenString = localStorage.getItem('token');
    if (tokenString) {
      const token = JSON.parse(tokenString).access_token;
      return token;
    }
    return null;
  }

  logout() {
    localStorage.removeItem('token');
  }

  getTokenData() {
    const token = this.getTokenLocalstorage();
    if (token) {
      const tokenData = this.jwtHelper.decodeToken(token);
      return tokenData;
    }
    return null;
  }

  getTokenDataAdditionalInfo() {
    const tokenDataAdditionalInfo = localStorage.getItem('token');
    if (tokenDataAdditionalInfo) {
      return JSON.parse(tokenDataAdditionalInfo);
    }
    return null;
  }

  hasAnyRoles(role: string) {
    const token = this.getTokenLocalstorage();
    if (token) {
      const roles = this.jwtHelper.decodeToken(token).authorities;
      for (var i = 0; i < roles.length; i++) {
        if (role.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  getUserRoles() {
    const token = this.getTokenLocalstorage();
    if (token) {
      return this.jwtHelper.decodeToken(token).authorities;
    }
    return null;
  }
}
