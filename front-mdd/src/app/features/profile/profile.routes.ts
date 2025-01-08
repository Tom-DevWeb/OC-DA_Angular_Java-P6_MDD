import {Routes} from '@angular/router';
import {AuthGuard} from '../../core/auth/guards/auth.guard';
import {UpdateProfileComponent} from './components/update-profile/update-profile.component';
import {ProfileLayoutComponent} from './layout/profile-layout/profile-layout.component';


export const routes: Routes = [
  {
    path: '',
    component: ProfileLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: '',
        pathMatch: 'full',
      },
      {
        path: '',
        component: UpdateProfileComponent,
        canActivate: [AuthGuard],
      }
    ]},
]
