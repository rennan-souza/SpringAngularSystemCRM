import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Customer, CustomerPage } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  apiBaseUrl: string = environment.apiUrlBase;

  constructor(
    private http: HttpClient
  ) { }

  findAll(page: number, size: number, search: string): Observable<CustomerPage> {
    return this.http.get<CustomerPage>(`${this.apiBaseUrl}/customers?page=${page}&size=${size}&sort=id,desc&search=${search}`);
  }

  save(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.apiBaseUrl}/customers`, customer);
  }

  findById(id: number) {
    return this.http.get<Customer>(`${this.apiBaseUrl}/customers/${id}`);
  }

  update(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiBaseUrl}/customers/${customer.id}`, customer);
  }

  delete(id: number) {
    return this.http.delete<Customer>(`${this.apiBaseUrl}/customers/${id}`);
  }
}
