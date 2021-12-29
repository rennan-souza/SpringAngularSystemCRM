import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/models/profile';
import { UserSigninData } from 'src/app/models/userSigninData';
import { AuthService } from 'src/app/services/auth.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profile: Profile = new Profile();
  userSigninData: UserSigninData = new UserSigninData('', '');
  loader: boolean = false;

  constructor(
    private profileService: ProfileService,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    let params: Observable<Params> = this.activatedRoute.params
    params.subscribe(urlParams => {
      this.profileService.show().subscribe(response => {
        this.profile = response;
      }, errorResponse => {
        errorResponse = this.router.navigate(['/']);
      })
    })
  }

  update() {
    this.profileService.update(this.profile).subscribe(response => {

      this.userSigninData.username = this.profile.email ? this.profile.email : ''
      this.userSigninData.password = this.profile.password ? this.profile.password : ''

      this.authService.signin(this.userSigninData).subscribe(response => {
        const access_token = JSON.stringify(response);
        localStorage.setItem('token', access_token);
      });

      this.toastr.success("Dados editado", "Sucesso");
      
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.error_description, "Erro");
    });
  }
}
