import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { EmployeeListComponent } from './employee/employee-list/employee-list.component';
import { EmployeeSingleComponent } from './employee/employee-single/employee-single.component';
import { HeaderComponent } from './header/header.component';
import { EmployeeService } from "./services/employee.service";
import { RouterModule, Routes} from "@angular/router";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {NgIconsModule} from "@ng-icons/core";
import {
  heroArrowRightOnRectangle,
  heroBars3,
  heroBuildingOffice, heroEye,
  heroUser,
  heroUserPlus, heroUsers, heroFaceFrown, heroXCircle, heroTrash, heroPlus
} from "@ng-icons/heroicons/outline";
import {heroUserSolid, heroUsersSolid} from "@ng-icons/heroicons/solid";
import { EmployeeEditComponent } from './employee/employee-edit/employee-edit.component';
import { EmployeeAddComponent } from './employee/employee-add/employee-add.component';
import { SigninComponent } from './auth/signin/signin.component';
import {AuthGuardService} from "./services/auth-guard.service";
import { WelcomeComponent } from './employee/welcome/welcome.component';
import { DepartmentListComponent } from './department/department-list/department-list.component';
import {HasRoleGuardService} from "./services/has-role-guard.service";
import {RequestInterceptorProvider} from "./interceptors/request.interceptor";
import { DepartmentAddComponent } from './department/department-add/department-add.component';

const routes:Routes = [
  {path: 'employees', canActivate:[AuthGuardService],component: EmployeeListComponent},
  {path: 'employees/add', canActivate:[AuthGuardService], component: EmployeeAddComponent},
  {path: 'employees/:id', canActivate:[AuthGuardService], component: EmployeeSingleComponent},
  {path: 'employees/edit/:id', canActivate:[AuthGuardService], component: EmployeeEditComponent},
  {path: 'departments', canActivate:[HasRoleGuardService], component: DepartmentListComponent},
  {path: 'departments/add', canActivate:[AuthGuardService], component: DepartmentAddComponent},
  {path: 'signin', component:SigninComponent},
  {path: '', redirectTo:'employees', pathMatch:"full"},
  {path: '**', redirectTo:'employees'}
]
@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeSingleComponent,
    HeaderComponent,
    EmployeeEditComponent,
    EmployeeAddComponent,
    SigninComponent,
    WelcomeComponent,
    DepartmentListComponent,
    DepartmentAddComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    ReactiveFormsModule,
    NgIconsModule.withIcons({heroArrowRightOnRectangle, heroUserPlus, heroBars3, heroUser, heroUserSolid,
    heroUsersSolid, heroBuildingOffice, heroEye, heroUsers, heroFaceFrown, heroXCircle, heroTrash, heroPlus }),
  ],
  providers: [
    EmployeeService,
    HttpClient,
    RequestInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
