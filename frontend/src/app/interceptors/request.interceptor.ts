import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS, HttpHeaders, HttpErrorResponse
} from '@angular/common/http';
import {BehaviorSubject, catchError, filter, Observable, of, switchMap, take, throwError} from 'rxjs';
import {TokenService} from "../services/token.service";
import {AuthService} from "../services/auth.service";
import {Token} from "../models/Token";

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  isRefreshing:boolean=false;
  refreshSubject:BehaviorSubject<any>= new BehaviorSubject<any>(null);
  constructor(private tokenService:TokenService, private authService:AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token=this.tokenService.getToken();

      if (token){
        const clonedReq=request.clone({
          headers:new HttpHeaders({'Authorization':`Bearer ${token}`})
        });

        return next.handle(clonedReq);
        //   .pipe(catchError(err  => {
        //   console.log('ERREUR :'+err.status);
        //
        //   if (err instanceof HttpErrorResponse && !request.url.includes('/signin') && err.status === 401){
        //     return this.handled401Error(request, next);
        //   }else{
        //     return throwError(err);
        //   }
        // }));

      }
      else
      return next.handle(request);
  }

  // addToken(request: HttpRequest<unknown>, token:string):HttpRequest<unknown>{
  //    let auth = request.clone().headers.set('Authorization',`Bearer ${token}`);
  //    return request.clone({headers: auth})
  // }

  // handled401Error(request:HttpRequest<unknown>, next: HttpHandler){
  //   if (!this.isRefreshing){
  //     this.isRefreshing=true;
  //     this.refreshSubject.next(null);
  //
  //     const token=this.tokenService.getRefreshToken();
  //     // console.log('token :'+token);
  //     // console.log('requeste :'+request);
  //
  //
  //     if(token)
  //     return this.authService.refreshToken(token).pipe(
  //       switchMap((token)=>{
  //         this.isRefreshing=false;
  //         this.tokenService.saveToken(token);
  //         this.refreshSubject.next(token.access_token);
  //         return next.handle(this.addToken(request, token.access_token));
  //       }),
  //
  //       catchError((err) => {
  //         this.isRefreshing = false;
  //         this.tokenService.clearToken();
  //         return throwError(err);
  //       })
  //     );
  //   }
  //     return this.refreshSubject.pipe(
  //       filter(token => token != null),
  //       take(1),
  //       switchMap(access_token => {
  //         return next.handle(this.addToken(request, access_token))
  //       }));
  //
  // }
}

export const RequestInterceptorProvider={
  provide:HTTP_INTERCEPTORS,
  useClass:RequestInterceptor,
  multi:true
}
