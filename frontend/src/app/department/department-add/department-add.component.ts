import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-department-add',
  templateUrl: './department-add.component.html',
  styleUrls: ['./department-add.component.css']
})
export class DepartmentAddComponent implements OnInit {

  addDepForm: FormGroup | any;
  constructor(private formBuilder:FormBuilder, private router:Router) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    this.addDepForm= this.formBuilder.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: ['']
    });
  }

  onSubmit(){

  }
}
