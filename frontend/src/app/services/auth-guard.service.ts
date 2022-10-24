import { Injectable } from '@angular/core';
import {ActivatedRoute, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(private router:Router, private authService:AuthService) { }

  canActivate(route: ActivatedRoute,
              state:RouterStateSnapshot):
    boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree>{


    if(this.authService.isLogged){
       const roles:string[]=[];
       roles.push(localStorage.getItem('roles') as string);

      return  !(roles.includes('USER'));
    }

    return this.router.navigate(["signin"]);
  }
}
