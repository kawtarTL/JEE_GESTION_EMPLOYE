import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Subscription} from "rxjs";
import {UserToken} from "../models/UserToken";

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
  userTokenSubscription:Subscription=new Subscription();
  userToken:UserToken= {
    audience: [],
    expiration_date: new Date(),
    issuer: '',
    roles: [],
    subject: ''
  }

  constructor(public authService:AuthService) { }

  ngOnDestroy(): void {
    this.userTokenSubscription.unsubscribe();
  }

  ngOnInit(): void {
    this.authService.userToken.subscribe(
      (u)=>{this.userToken=u}
    );
  }

  lougout(){
    this.authService.destroySession();
    this.userTokenSubscription.unsubscribe();
  }

}
