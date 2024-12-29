import {Routes} from '@angular/router';
import {CreateArticleComponent} from './create-article/create-article.component';
import {ListArticleComponent} from './list-article/list-article.component';


export const routes: Routes = [
  {
    path: '',
    component: ListArticleComponent,
    children: [
      {
        path: 'create',
        component: CreateArticleComponent,
      }
    ]},
]
