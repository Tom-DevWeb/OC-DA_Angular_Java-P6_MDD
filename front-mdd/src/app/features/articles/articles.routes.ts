import {Routes} from '@angular/router';
import {CreateArticleComponent} from './create-article/create-article.component';
import {ListArticleComponent} from './list-article/list-article.component';
import {AuthGuard} from '../../core/auth/guards/auth.guard';


export const routes: Routes = [
  {
    path: '',
    component: ListArticleComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'create',
        component: CreateArticleComponent,
      }
    ]},
]
