import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.scss']
})
export class CustomerEditComponent implements OnInit {

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
          this.customer = response;
        }, errorResponse => {
          errorResponse = this.router.navigate(['/clientes']);
        })
      }
    })
  }

  update() {
    this.loader = true
    this.customerService.update(this.customer).subscribe(response => {
      this.toastr.success("Cliente editado", "Sucesso");
      this.router.navigate(['/clientes'])
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }

}
