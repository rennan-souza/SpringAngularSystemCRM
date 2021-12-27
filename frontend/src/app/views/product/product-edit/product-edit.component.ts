import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Category } from 'src/app/models/category';
import { Product, ProductResponse } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {

  product: Product;
  id: number = 0;
  imageSrc: any;
  fileImage: any;
  loader: boolean = false;
  categories: Category[] = [];
  category: Category = { id: 0, name: '' }

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
    this.findAllCategories();

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


  findAllCategories() {
    this.categoryService.findAll().subscribe(response => {
      this.categories = response
    })
  }

  onFileChange(event: any) {
    const reader = new FileReader();
    const [file] = event.target.files;

    const imgExtension: string[] = ["image/png", "image/jpg", "image/jpeg"];

    if (!imgExtension.includes(file.type)) {
      alert('A imagem deve ser do tipo: PNG, JPG ou JPEG');
      this.product.file = '';
    } else {
      this.fileImage = file;

      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageSrc = reader.result as string;
      }
    }
  }

  update() {

    this.loader = true
    const formData = new FormData();
    formData.append('file', this.fileImage);
    formData.append('name', this.product.name || '');
    formData.append('description', this.product.description || '');
    formData.append('price', this.product.price as any);
    formData.append('category', this.product.category?.id as any);

    this.productService.update(formData as Product, this.id).subscribe(() => {
      this.toastr.success("Produto editado.", "Sucesso");
      this.router.navigate(['/produtos']);
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }
}
