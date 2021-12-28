import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-delete',
  templateUrl: './product-delete.component.html',
  styleUrls: ['./product-delete.component.scss']
})
export class ProductDeleteComponent implements OnInit {

  product: Product;
  id: number = 0;
  imageSrc: any;
  fileImage: any;
  loader: boolean = false;
  category: Category = {id: 0, name: ''}

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
  ) {
    this.product = new Product();
  }

  ngOnInit(): void {
    let params: Observable<Params> = this.activatedRoute.params
    params.subscribe(urlParams => {
      this.id = urlParams['id'];
      if (this.id) {
        this.productService.findById(this.id).subscribe(response => {
          this.product = response;
          this.imageSrc = response.imgBase64
          this.category = response.category
        }, errorResponse => {
          errorResponse = this.router.navigate(['/produtos']);
        })
      }
    })
  }

  delete() {
    this.loader = true
    this.productService.delete(this.product.id ? this.product.id : 0).subscribe(response => {
      this.toastr.success("Produto excluído", "Sucesso");
      this.router.navigate(['/produtos'])
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }
}
