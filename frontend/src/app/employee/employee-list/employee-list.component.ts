import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {Employee} from "../../models/Employee";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit, OnDestroy {

  employees: Employee[] | any;
  employeesSubscription: Subscription = new Subscription();

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.getAllEmployees();
  }

  getAllEmployees(){
    this.employeesSubscription = this.employeeService.getEmployees().subscribe(
      (data)=>{
        this.employees = data;
        //console.log('data: ', data);
      },
      (error)=>{console.log(error)}
    );
  }

  ngOnDestroy() {
    this.employeesSubscription.unsubscribe();
  }
}
