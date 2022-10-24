import {Injectable, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {BehaviorSubject, Observable, Subject, tap} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/User";
import {Token} from "../models/Token";
import {TokenService} from "./token.service";
import {UserToken} from "../models/UserToken";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl:string= 'http://localhost:8080/login';
  refreshUrl:string= 'http://localhost:8080/refreshToken';

  user:User={
    email:'',
    password:''
  };

  isLogged:Subject<boolean>=new Subject<boolean>();
  userToken: Subject<UserToken>=new Subject<UserToken>();

  constructor(private router:Router,
              private http:HttpClient,
              private tokenService:TokenService) {}

  signInUser(user:User):Observable<Token>{
    return this.http.post<Token>(this.apiUrl, user, {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    }).pipe(
      tap((response:Token)=>{
        this.isLogged.next(true);
        this.tokenService.saveToken(response);
        this.userToken.next(this.getUserFromToken(response.access_token));
        let user=this.getUserFromToken(response.access_token);
        localStorage.setItem('roles', user.roles.toString());
        if(user.roles.includes('ADMIN'))
          this.router.navigate(['employees']);
        else
          this.router.navigate(['departments']);
      })
    );
  }

  getUserFromToken(token: string):UserToken{
    return JSON.parse(atob(token.split(".")[1])) as UserToken;
  }

  isConnected():boolean{
    const token:string=this.tokenService.getToken();
    return !token;
  }

  destroySession(){
    this.tokenService.clearToken();
    this.isLogged.next(false);
    this.router.navigate(['signin']);
  }

  refreshToken(refreshToken:string):Observable<Token>{
    return this.http.get<Token>(this.refreshUrl, {headers:new HttpHeaders({
        'Authorization':`${refreshToken}`
      })})
      .pipe((
        tap((token:Token)=>{
          this.tokenService.saveToken(token);
        })
  ));
  }
}
