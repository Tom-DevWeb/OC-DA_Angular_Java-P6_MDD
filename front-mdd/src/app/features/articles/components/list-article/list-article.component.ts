import {Component, OnInit} from '@angular/core';
import {Button, ButtonDirective} from 'primeng/button';
import {ArticleService} from '../../services/article.service';
import {ArticleResponse} from '../../models/articleResponse';
import {AsyncPipe, DatePipe, NgFor, NgIf, TitleCasePipe} from '@angular/common';
import {DataView} from 'primeng/dataview';
import {map, Observable} from 'rxjs';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-list-article',
  imports: [
    Button,
    ButtonDirective,
    NgIf,
    DataView,
    AsyncPipe,
    NgFor,
    DatePipe,
    TitleCasePipe,
    RouterLink
  ],
  templateUrl: './list-article.component.html',
  styleUrl: './list-article.component.scss'
})
export class ListArticleComponent implements OnInit {

  public articles$!: Observable<ArticleResponse[]>

  sortOrder: number = 0

  sortField: string = 'title'

  constructor(private articleService: ArticleService) {
  }


  ngOnInit() {
    this.getArticles()
  }

  toggleSortOrder() {
      this.sortOrder = this.sortOrder === 1 ? -1 : 1
  }

  getArticles() {
    this.articles$ = this.articleService.getArticles().pipe(
      map((articles: ArticleResponse[]) =>
      articles.sort((a, b) => {
        const dateA = new Date(a.createdAt).getTime()
        const dateB = new Date(b.createdAt).getTime()
        return dateB - dateA
      })
      )
    )
    this.articles$.subscribe({
      error: (error) => {
        console.error(error);
      }
    })
  }



}
