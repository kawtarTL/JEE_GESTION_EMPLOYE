import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/User";
import {Token} from "../../models/Token";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  user: User | any;
  loginForm: FormGroup | any;
  errorMessage: string | undefined;

  constructor(private router:Router,
              private formBuilder:FormBuilder,
              private authService:AuthService) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    this.loginForm = this.formBuilder.group({
        email:['', [Validators.email, Validators.required]],
        password:['', [Validators.required, Validators.pattern(/[0-9a-zA-Z]{5,}/)]]
      });
  }

  onSubmit(){
    this.user={
      email: this.loginForm.get('email').value,
      password: this.loginForm.get('password').value
    }

    this.authService.signInUser(this.user).subscribe(
      (data:Token)=>{
        //console.log(data);
      },
      (err)=>{
        console.log(err);
        this.errorMessage=err.error['message'];
      }
    );
  }

}
