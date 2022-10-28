import {Injectable, OnDestroy} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable, Subscription} from "rxjs";
import {AuthService} from "./auth.service";
import {UserToken} from "../models/UserToken";

@Injectable({
  providedIn: 'root'
})
export class HasRoleGuardService implements CanActivate, OnDestroy{

  isLoggedSubscription= new Subscription();
  isLogged:boolean= !!localStorage.getItem("access_token");
  userTokenSubscription= new Subscription();
  userToken:UserToken= this.authService.getUserFromToken(localStorage.getItem("access_token") as string);

  constructor(private router:Router, private authService:AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    this.isLoggedSubscription= this.authService.isLogged_.subscribe(
      isLogged => this.isLogged= isLogged
    );

    this.userTokenSubscription= this.authService.userToken_.subscribe(
      userToken => this.userToken= userToken
    );

    if(this.isLogged && (this.userToken.roles.includes("USER") || this.userToken.roles.includes("ADMIN"))){
      return true;
    }

    return this.router.navigate(['signin']);
  }

  ngOnDestroy() {
    this.isLoggedSubscription.unsubscribe();
    this.userTokenSubscription.unsubscribe();
  }
}
