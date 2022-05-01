import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { TokenStorageService } from '../service/token-storage.service';
import { DataSharingService } from '../service/datasharing.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  constructor(private router: Router, private tokenStorage: TokenStorageService,
              private dataSharingService: DataSharingService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
  }

  isLoggedIn = false;

  ngOnInit(): void {
    if (this.isLoggedIn) {
      this.dataSharingService.curUser.next(this.getCurrentUser());
      switch (this.getCurrentRole()) {
        case "ADMIN":
          this.router.navigate(['/registration']);
          break;
        case "TEACHER":
          this.router.navigate(['/my-courses']);
          break;
        case "STUDENT":
          this.router.navigate(['/courses']);
          break;
      }
    } else
      this.openAuthorization();
  }

  getCurrentUser(): string {
    return this.tokenStorage.getUser().name;
  }

  getCurrentRole() : string {
    return this.tokenStorage.getUser().role;
  }

  openAuthorization(): void{
    this.router.navigate(['/authorization']);
  }

}
