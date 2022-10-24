import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {EmployeeService} from "../../services/employee.service";
import {Employee} from "../../models/Employee";

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {

  employeeForm:FormGroup | any;
  employee: Employee={
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

  constructor(private formBuilder:FormBuilder,
              private employeeService:EmployeeService,
              private router:Router) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    this.employeeForm = this.formBuilder.group({
      firstName:['', [Validators.required]],
      lastName:['', [Validators.required]],
      email:['', [Validators.email, Validators.required]],
      password:['password'],
      genre:['', Validators.required],
      type:['', Validators.required],
      startWork:['', Validators.required],
      salary:['', Validators.required],
      birthDate:['', Validators.required]
    });
  }

  submit(){

    this.employee.firstName = this.employeeForm.get('firstName').value;
    this.employee.lastName = this.employeeForm.get('lastName').value;
    this.employee.email = this.employeeForm.get('email').value;
    this.employee.genre = this.employeeForm.get('genre').value;
    this.employee.type = this.employeeForm.get('type').value;
    this.employee.password = this.employeeForm.get('password').value;
    this.employee.startWork = this.employeeForm.get('startWork').value;
    this.employee.salary = this.employeeForm.get('salary').value;
    this.employee.birthDate=this.employeeForm.get('birthDate').value;

    console.log(this.employee);

    this.employeeService.addEmployee(this.employee).subscribe((
      (data)=>{
        console.log(data);
        this.employee = data;
        this.router.navigate(['employees']);
      }
    ));
  }

}
