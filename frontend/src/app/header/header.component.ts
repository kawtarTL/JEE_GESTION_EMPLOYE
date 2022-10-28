import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {UserToken} from "../models/UserToken";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  navigation = [
    // {title:'Employés', url:'/employees'},
    {title:'Départements', url:'/departments'},
  ];
  isLoggedSubscription= new Subscription();
  isLogged:boolean=!!localStorage.getItem("access_token");
  userTokenSubscription= new Subscription();
  userT:UserToken={
    roles:[],
    subject:'',
    issuer:'',
    audience:[],
    expiration_date:new Date()
  }
  userToken= this.isLogged? this.authService.getUserFromToken(localStorage.getItem("access_token") as string) : this.userT ;

  constructor(public authService:AuthService) { }

  ngOnInit(): void {
    this.isLoggedSubscription= this.authService.isLogged_.subscribe(
      isLogged => this.isLogged=isLogged
    );

    this.userTokenSubscription= this.authService.userToken_.subscribe(
      userToken => this.userToken=userToken
    );
  }

  lougout(){
    this.authService.destroySession();
  }

  ngOnDestroy() {
    this.userTokenSubscription.unsubscribe();
    this.isLoggedSubscription.unsubscribe();
  }

}
