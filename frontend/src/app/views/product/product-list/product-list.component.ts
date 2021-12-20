import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Category } from 'src/app/models/category';
import { ProductResponse } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  tokenData: any;
  progressBar: boolean = false;
  totalElements = 0;
  page = 0;
  size = 5;
  products: ProductResponse[] = []
  categories: Category[] = [];
  productIdSearch: number = 0;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.findAllCategories();
    this.findAll();
  }

  findAllCategories() {
    this.categoryService.findAll().subscribe(response => {
      this.categories = response
    })
  }

  findAll() {
    this.progressBar = true;
    this.productService.findAll(this.page, this.size, this.productIdSearch).subscribe(response => {
      response.content ? this.products = response.content : ''
      this.totalElements = response.totalElements;
      this.page = response.number;
      this.progressBar = false;
    }, errorReponse => {
      this.progressBar = false
    }) 
  }

  search(value: number) {
    this.productIdSearch = value; 
    this.findAll();
  }

  paginate(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize
    this.findAll(); 
  }
}
