import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Profile, UpdatePassword } from '../models/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  apiBaseUrl: string = environment.apiUrlBase;

  constructor(
    private http: HttpClient
  ) { }

  show() {
    return this.http.get<Profile>(`${this.apiBaseUrl}/profile`);
  }

  update(profile: Profile): Observable<Profile> {
    return this.http.put<Profile>(`${this.apiBaseUrl}/profile`, profile);
  }

  updatePassword(obj: UpdatePassword): Observable<UpdatePassword> {
    return this.http.put<UpdatePassword>(`${this.apiBaseUrl}/profile/change-password`, obj);
  }
}
