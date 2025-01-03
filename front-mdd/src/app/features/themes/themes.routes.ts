import {Routes} from '@angular/router';
import {AuthGuard} from '../../core/auth/guards/auth.guard';
import {HeaderLayoutComponent} from '../../shared/layout/header-layout/header-layout.component';
import {ListThemeComponent} from './components/list-theme/list-theme.component';


export const routes: Routes = [
  {
    path: '',
    component: HeaderLayoutComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        component: ListThemeComponent,
        canActivate: [AuthGuard],
      }
    ]},
]
