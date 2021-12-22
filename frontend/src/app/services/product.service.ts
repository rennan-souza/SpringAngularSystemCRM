import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product, ProductPage } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  apiBaseUrl: string = environment.apiUrlBase;

  constructor(
    private http: HttpClient
  ) { }

  findAll(page: number, size: number, categoryId: number): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${this.apiBaseUrl}/products?page=${page}&size=${size}&sort=id,desc&categoryId=${categoryId}`);
  }

  save(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiBaseUrl}/products`, product);
  }
}
