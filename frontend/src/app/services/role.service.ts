import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Role } from '../models/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  apiBaseUrl: string = environment.apiUrlBase;

  constructor(
    private http: HttpClient
  ) { }

  findAll(): Observable<Role[]>  {
    return this.http.get<Role[]>(`${this.apiBaseUrl}/roles`);
  }

  formatRole = (value: string) => {
    if (value === 'ROLE_OPERATOR') {
      return 'OPERADOR';
    } else if (value === 'ROLE_ADMIN') {
      return 'ADMIN'
    } else {
      return '';
    }
  };
}
