import {Component, OnDestroy, OnInit} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {Department} from "../../models/Department";
import {Subscription} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {UserToken} from "../../models/UserToken";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent implements OnInit, OnDestroy {

  departments?: Department[];
  department:Department={
    name:'',
    code:'',
    description:''
  };
  userToken:UserToken= {
    audience: [],
    expiration_date: new Date(),
    issuer: '',
    roles: [],
    subject: ''
  }
  subscriptionDepartments:Subscription = new Subscription();
  userTokenSubscription:Subscription=new Subscription();

  constructor(private departmentService:DepartmentService,
              public authService:AuthService) { }

  ngOnInit(): void {
    this.getDepartments();
    this.authService.userToken.subscribe(
      (u)=>{this.userToken=u}
    );
  }

  getDepartments(){
    this.departmentService.getDepartments().subscribe(
      (data)=>{
        this.departments=data;
        console.log(data);
      }
    );
  }

  ngOnDestroy(): void {
    this.subscriptionDepartments.unsubscribe();
    this.userTokenSubscription.unsubscribe();
  }
}
