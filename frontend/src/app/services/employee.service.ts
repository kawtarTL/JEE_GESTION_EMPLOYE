import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Employee} from "../models/Employee";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrl:string = environment.BASE_URL + 'employees';
  employees:Employee[] | any;
  employee:Employee | any;
  employeesSubject = new Subject<Employee>;
  employeeSubject = new Subject<Employee>;
  token:string|any;

  constructor(private http: HttpClient) {}

  emitEmployees(){
    this.employeesSubject.next(this.employees);
  }

  emitEmployee(){
    this.employeeSubject.next(this.employee);
  }

  getEmployees(){
    this.token= localStorage.getItem('access_token');
      return  this.http.get<Employee[]>(this.apiUrl);
  }

  addEmployee(employee:Employee):Observable<Employee>{
    this.token= localStorage.getItem('access_token');
    let emp = this.http.post<Employee>(this.apiUrl+'/add', employee);

    this.emitEmployees();
    return emp;
  }

  getSingleEmployee(id:number):Observable<Employee>{
    this.token= localStorage.getItem('access_token');
    let emp= this.http.get<Employee>(this.apiUrl+`/${id}`);
    this.emitEmployee();
    return emp;
  }
}
