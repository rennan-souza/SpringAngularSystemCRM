import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-register',
  templateUrl: './product-register.component.html',
  styleUrls: ['./product-register.component.scss']
})
export class ProductRegisterComponent implements OnInit {

  product: Product;
  imageSrc: any;
  fileImage: any;
  loader: boolean = false;
  categories: Category[] = [];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private toastr: ToastrService,
  ) { 
    this.product = new Product();
  }

  ngOnInit(): void {
    this.findAllCategories();
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

  save() {
    this.loader = true
    const formData = new FormData();
    formData.append('file', this.fileImage);
    formData.append('name', this.product.name || '');
    formData.append('description', this.product.description || '');
    formData.append('price', this.product.price as any);
    formData.append('category', this.product.category as any);

    this.productService.save(formData as Product).subscribe(() => {
      this.toastr.success("Produto cadastrado.", "Sucesso");
      this.router.navigate(['/produtos']);
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }

}
