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

  userT:UserToken={
    roles:[],
    subject:'',
    issuer:'',
    audience:[],
    expiration_date:new Date()
  }
  private token:string= localStorage.getItem("access_token") as string;
  public isLogged:boolean=!!this.token;
  public isLogged_= new BehaviorSubject<boolean>(this.isLogged);
  public userToken= this.isLogged? this.getUserFromToken(this.token) : this.userT ;
  public userToken_= new BehaviorSubject(this.userToken);

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
        this.tokenService.saveToken(response);
        this.userToken_.next(this.getUserFromToken(response.access_token));
        this.isLogged_.next(true);
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

  destroySession(){
    this.tokenService.clearToken();
    this.isLogged_.next(false);
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
