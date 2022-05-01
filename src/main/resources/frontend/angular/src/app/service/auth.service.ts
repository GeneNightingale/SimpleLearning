import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  login(credentials : any): Observable<any> {
    return this.http.post(this.apiServerUrl + '/authenticate', {
      login: credentials.login,
      password: credentials.password
    }, httpOptions);
  }

  register(user : any): Observable<any> {
    return this.http.post(this.apiServerUrl + '/register', {
      name: user.name,
      login: user.login,
      password: user.password,
      role: user.role
    }, httpOptions);
  }

}
