import {Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {UnauthGuard} from './guards/unauth.guard';
import {AuthComponent} from './layout/auth/auth.component';

export const routes: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
      },
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [UnauthGuard],
      },
      {
        path: 'register',
        component: RegisterComponent,
        canActivate: [UnauthGuard],
      }
    ]},
]
