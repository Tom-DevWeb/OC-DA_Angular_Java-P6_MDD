import {Routes} from '@angular/router';
import {CreateArticleComponent} from './components/create-article/create-article.component';
import {ListArticleComponent} from './components/list-article/list-article.component';
import {AuthGuard} from '../../core/auth/guards/auth.guard';
import {ArticlesComponent} from './layout/articles/articles.component';


export const routes: Routes = [
  {
    path: '',
    component: ArticlesComponent,
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      },
      {
        path: 'list',
        component: ListArticleComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'create',
        component: CreateArticleComponent,
        canActivate: [AuthGuard],
      }
    ]},
]
