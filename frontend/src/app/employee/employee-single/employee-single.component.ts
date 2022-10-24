import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {EmployeeService} from "../../services/employee.service";
import {Employee} from "../../models/Employee";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-employee-single',
  templateUrl: './employee-single.component.html',
  styleUrls: ['./employee-single.component.css']
})
export class EmployeeSingleComponent implements OnInit, OnDestroy {

  employee: Employee ={
    firstName:'',
    lastName:'',
    email:'',
    genre:'',
    type:'',
    salary:0,
    password:'',
    startWork:new Date(),
    birthDate:new Date()
  };
  subscriptionEmployee= new Subscription();

  constructor(private activatedRoute:ActivatedRoute,
              private employeeService:EmployeeService) { }

  ngOnInit(): void {
   this.getEmployee();
  }

  getEmployee(){
    this.subscriptionEmployee= this.employeeService.getSingleEmployee(+this.activatedRoute.snapshot.params['id']).subscribe(
      (data:Employee)=>{
        this.employee= data;
        //console.log(this.employee);
      },
      (error)=>{console.log(error)}
    );
  }

  ngOnDestroy(): void {
    this.subscriptionEmployee.unsubscribe();
  }
}
