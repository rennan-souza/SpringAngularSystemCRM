import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss']
})
export class CustomerListComponent implements OnInit {


  progressBar: boolean = false;
  totalElements = 0;
  page = 0;
  size = 5;
  customers: Customer[] = []
  searchCustomer: string = '';

  constructor(
    private customerService: CustomerService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.progressBar = true;
    this.customerService.findAll(this.page, this.size, this.searchCustomer).subscribe(response => {
      response.content ? this.customers = response.content : ''
      this.totalElements = response.totalElements;
      this.page = response.number;
      this.progressBar = false;
    }, errorReponse => {
      this.progressBar = false
    }) 
  }

  search() {
    this.findAll();
  }

  paginate(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize
    this.findAll(); 
  }
}
