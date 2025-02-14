import { Routes } from '@angular/router';
import {HomeComponent} from './core/layouts/home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'auth',
    loadChildren: () => import('./core/auth/auth.routes').then(m => m.routes)
  },
  {
    path: 'articles',
    loadChildren: () => import('./features/articles/articles.routes').then(m => m.routes)
  },
  {
    path: 'themes',
    loadChildren: () => import('./features/themes/themes.routes').then(m => m.routes)
  },
  {
    path: 'profile',
    loadChildren: () => import('./features/profile/profile.routes').then(m => m.routes)
  }
];
