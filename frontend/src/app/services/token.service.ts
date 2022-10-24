import {Injectable} from '@angular/core';
import {Token} from "../models/Token";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  saveToken(token:Token):void{
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');

    localStorage.setItem('access_token', token.access_token);
    localStorage.setItem('refresh_token', token.refresh_token);
  }

  clearToken():void{
    localStorage.clear();
  }

  getToken():string{
    return localStorage.getItem('access_token') as string;
  }

  getRefreshToken():string{
    return localStorage.getItem('refresh_token') as string;
  }
}
