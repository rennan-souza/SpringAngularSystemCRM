import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User, UserPage } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  apiBaseUrl: string = environment.apiUrlBase;

  constructor(
    private http: HttpClient
  ) { }

  findAll(page: number, size: number, search: string): Observable<UserPage> {
    return this.http.get<UserPage>(`${this.apiBaseUrl}/users?page=${page}&size=${size}&sort=id,desc&search=${search}`);
  }

  save(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiBaseUrl}/users`, user);
  }

  findById(id: number) {
    return this.http.get<User>(`${this.apiBaseUrl}/users/${id}`);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiBaseUrl}/users/${user.id}`, user);
  }

  delete(id: number) {
    return this.http.delete<User>(`${this.apiBaseUrl}/users/${id}`);
  }
}
