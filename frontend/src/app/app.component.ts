import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "./services/auth.service";
import {Subscription} from "rxjs";
import {UserToken} from "./models/UserToken";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{

  title = 'gesembFront';
  userTokenSubscription= new Subscription();
  userT:UserToken={
    roles:[],
    subject:'',
    issuer:'',
    audience:[],
    expiration_date:new Date()
  }
  isLoggedSubscription= new Subscription();
  isLogged:boolean = !!localStorage.getItem("access_token");
  userToken= this.isLogged? this.authService.getUserFromToken(localStorage.getItem("access_token") as string) : this.userT ;
  constructor(public authService:AuthService) {}

  ngOnInit(): void {
    this.isLoggedSubscription = this.authService.isLogged_.subscribe(
      isLogged => this.isLogged= isLogged
    );

    this.userTokenSubscription= this.authService.userToken_.subscribe(
      userToken => this.userToken= userToken
    );
  }

  ngOnDestroy() {
    this.isLoggedSubscription.unsubscribe();
    this.userTokenSubscription.unsubscribe();
  }
}
