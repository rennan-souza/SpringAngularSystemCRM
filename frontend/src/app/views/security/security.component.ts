import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UpdatePassword } from 'src/app/models/profile';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.scss']
})
export class SecurityComponent implements OnInit {

  obj: UpdatePassword = new UpdatePassword();
  loader: boolean = false;

  constructor(
    private profileService: ProfileService,
    private toastr: ToastrService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  update() {
    this.loader = true
    this.profileService.updatePassword(this.obj).subscribe(response => {
      this.toastr.success("Senha atualizada", "Sucesso");
      this.router.navigate(['/profile']);
    }, errorResponse => {
      this.loader = false
      this.toastr.error(errorResponse.error.error_description, "Erro");
    });
  }
}
