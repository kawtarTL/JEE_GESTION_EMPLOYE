import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Department} from "../models/Department";
import {environment} from "../../environments/environment";
import {TokenService} from "./token.service";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  apiUrl:string= environment.BASE_URL + "departments";
  departments:Department[] | any;
  subjectDepartment$ = new Subject<Department[]>;

  token:string | any;

  constructor(private router:Router,
              private http:HttpClient,
              private tokenService:TokenService) { }

  emitDepartments(){
    this.subjectDepartment$.next(this.departments);
  }

  getDepartments():Observable<Department[]>{
    this.token=this.tokenService.getToken();
    return this.http.get<Department[]>(this.apiUrl);
  }

  getSingleDepartment(id:number):Observable<Department>{
    this.token=this.tokenService.getToken();
    return this.http.get<Department>(this.apiUrl+ `/${id}`);
  }

  addNewDepartment(department:Department):Observable<Department>{
    this.token= this.tokenService.getToken();
    return this.http.post<Department>(this.apiUrl+'/add', department);
  }

  editDepartment(id:number, department:Department):Observable<Department>{
    this.token=this.tokenService.getToken();
    return this.http.put<Department>(this.apiUrl+ `/edit/${id}`, department)
  }

  deleteDepartment(id:number):Observable<string>{
    this.token=this.tokenService.getToken();
    return this.http.delete<string>(this.apiUrl+ `/delete/${id}`);
  }
}
