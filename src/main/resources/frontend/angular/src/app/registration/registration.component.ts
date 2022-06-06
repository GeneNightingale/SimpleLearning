import {User} from "../model/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {AppearanceService} from "../service/appearance.service";
import {TokenStorageService} from "../service/token-storage.service";
import {DataSharingService} from "../service/datasharing.service";

@Component({
    selector: 'app-registration',
    templateUrl: 'registration.component.html',
    styleUrls: ['./registration.component.css']
  })

export class RegistrationComponent {

  roles: any[] = [
    {
      name: "STUDENT",
      nameRus: "Студент",
    }, {
      name: "TEACHER",
      nameRus: "Вчитель",
    }, {
      name: "ADMIN",
      nameRus: "Адміністратор",
    }
  ]; //List of roles to populate dropdown with

  data: User | undefined;
  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(4)]),
    login: new FormControl('', [Validators.required, Validators.minLength(4)]),
    password: new FormControl('', [Validators.required, Validators.minLength(4)]),
    role: new FormControl('', [Validators.required])
  });

  get name() {
    return this.form.get('name');
  }

  get login() {
    return this.form.get('login');
  }

  get password() {
    return this.form.get('password');
  }

  get role() {
    return this.form.get('role');
  }

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router,
              private dataSharingService: DataSharingService, private appearanceService: AppearanceService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  user: User = new User('', '', '', '');

  isLoggedIn = false;

  errorMessage = 'error';

  ngOnInit() {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
      switch (this.getCurrentRole()) {
        case "ADMIN":
          //this.router.navigate(['/registration']);
          break;
        case "TEACHER":
          this.router.navigate(['/home']);
          break;
        case "STUDENT":
          this.router.navigate(['/home']);
          break;
      }
    }
  }

  //TODO: Get confirmation
  onSubmit() {
    if (this.form.valid) {
      this.authService.register(this.user).subscribe(
        data => {
          this.appearanceService.customAlert("Користувача успішно зареєстровано");
          console.log(data);
          this.form.reset();
        },
        err => {
          this.errorMessage = err.error.message;
          console.log(this.errorMessage);
          this.appearanceService.customAlert("Не вдалося зареєструвати користувача");
        }
      );
    }else {
      console.log("Invalid Form")
    }
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  getCurrentRole() : string {
    return this.tokenStorage.getUser().role;
  }
}
