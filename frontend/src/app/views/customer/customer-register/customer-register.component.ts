import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.scss']
})
export class CustomerRegisterComponent implements OnInit {

  customer: Customer = new Customer();
  loader: boolean = false;

  constructor(
    private customerService: CustomerService,
    private toastr: ToastrService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  save() {
    this.loader = true
    this.customerService.save(this.customer).subscribe(() => {
      this.toastr.success("Cliente cadastrado.", "Sucesso");
      this.router.navigate(['/clientes']);
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.message, "Erro");
    });
  }
}
