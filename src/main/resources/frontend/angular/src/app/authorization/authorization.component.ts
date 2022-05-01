import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../model/user";
import {Router} from "@angular/router";
import { AuthService } from '../service/auth.service';
import { TokenStorageService } from '../service/token-storage.service';
import { DataSharingService } from '../service/datasharing.service';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {
  data: User | undefined;
  form: FormGroup = new FormGroup({
    login: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]),
    password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)])
  });

  get login() {
    return this.form.get('login');
  }

  get password() {
    return this.form.get('password');
  }

  user: User = new User('', '', '', '');

  isLoggedIn = false;
  isLoginFailed = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router,
              private dataSharingService: DataSharingService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  public onSubmit() : void {
    this.authService.login(this.user).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data.user);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
      },
      err => {
        this.isLoginFailed = true;
      },
      () => {
        this.setCredentials();
        this.router.navigate(['/']);
      }
    );
  }

  setCredentials() : void {
    this.dataSharingService.isLoggedIn.next(true);
    this.dataSharingService.curUser.next(this.getCurrentUser());
    switch (this.getCurrentRole()) {
      case "ADMIN":
        this.dataSharingService.isAdmin.next(true);
        break;
      case "TEACHER":
        this.dataSharingService.isTeacher.next(true);
        break;
      case "STUDENT":
        this.dataSharingService.isStudent.next(true);
        break;
    }
  }

  reloadPage() : void {
    window.location.reload();
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  getCurrentRole() : string {
    return this.tokenStorage.getUser().role;
  }

  ngOnInit() : void {
    if (this.isLoggedIn)
      this.openHomePage();
  }

  openHomePage(): void{
    this.router.navigate(['/home']);
  }

}
