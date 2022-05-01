import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { TokenStorageService } from '../service/token-storage.service';
import { DataSharingService } from '../service/datasharing.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})

export class TopBarComponent implements OnInit {

  constructor(public router: Router, private tokenStorage: TokenStorageService, private dataSharingService: DataSharingService) {
    this.dataSharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    });
    this.dataSharingService.curUser.subscribe( value => {
      this.curUser = value;
    });
    this.dataSharingService.isAdmin.subscribe(value => {
      this.isAdmin = value;
    })
    this.dataSharingService.isTeacher.subscribe(value => {
      this.isTeacher = value;
    })
    this.dataSharingService.isStudent.subscribe(value => {
      this.isStudent = value;
    })
  }

  isLoggedIn : boolean = false;
  curUser : string = '';
  role : string = '';
  isAdmin : boolean = false;
  isTeacher : boolean = false;
  isStudent : boolean = false;

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.dataSharingService.isLoggedIn.next(true);
      this.getCurrentUser();
    } else
      this.dataSharingService.isLoggedIn.next(false);
    switch (this.tokenStorage.getUser().role) {
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

  nothing(): void {
    //this.router.navigate(['/authorization']);
  }

  openAuthorization(): void {
    this.router.navigate(['/authorization']);
  }

  openHomePage(): void{
    this.router.navigate(['/home']);
  }

  getCurrentUser(): void {
    this.curUser = this.tokenStorage.getUser().userName;
  }

  logOut() {
    this.tokenStorage.signOut();
    this.dataSharingService.isLoggedIn.next(false);
    this.dataSharingService.isAdmin.next(false);
    this.dataSharingService.isTeacher.next(false);
    this.dataSharingService.isStudent.next(false);
    this.openHomePage();
  }
}
