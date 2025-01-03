import {Routes} from '@angular/router';
import {CreateArticleComponent} from './components/create-article/create-article.component';
import {ListArticleComponent} from './components/list-article/list-article.component';
import {AuthGuard} from '../../core/auth/guards/auth.guard';
import {HeaderLayoutComponent} from '../../shared/layout/header-layout/header-layout.component';
import {ContentArticleComponent} from './components/content-article/content-article.component';


export const routes: Routes = [
  {
    path: '',
    component: HeaderLayoutComponent,
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
        path: ':articleId',
        component: ContentArticleComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'create',
        component: CreateArticleComponent,
        canActivate: [AuthGuard],
      }
    ]},
]
