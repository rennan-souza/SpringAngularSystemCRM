import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  tokenDataAdditionaInfo: any;
  tokenData: any;

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.tokenDataAdditionaInfo = this.authService.getTokenDataAdditionalInfo();
    this.tokenData = this.authService.getTokenData();
  }

}
