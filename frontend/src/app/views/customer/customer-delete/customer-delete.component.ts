import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer-delete',
  templateUrl: './customer-delete.component.html',
  styleUrls: ['./customer-delete.component.scss']
})
export class CustomerDeleteComponent implements OnInit {

  customer: Customer = new Customer();
  id: number = 0;
  loader: boolean = false;

  constructor(
    private customerService: CustomerService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    let params: Observable<Params> = this.activatedRoute.params
    params.subscribe(urlParams => {
      this.id = urlParams['id'];
      if (this.id) {
        this.customerService.findById(this.id).subscribe(response => {
          this.customer = response
        }, errorResponse => {
          errorResponse = this.router.navigate(['/clientes']);
        })
      }
    })
  }

  delete() {
    this.loader = true
    this.customerService.delete(this.customer.id ? this.customer.id : 0).subscribe(response => {
      this.toastr.success("Cliente excluído", "Sucesso");
      this.router.navigate(['/clientes'])
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }
}
