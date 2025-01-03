import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ArticleResponse} from '../models/articleResponse';
import {ArticleRequest} from '../models/articleRequest';
import {CommentResponse} from '../models/commentResponse';
import {CommentRequest} from '../models/commentRequest';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private baseUrl: string = "/api/articles"


  constructor(private http: HttpClient) { }

  getArticles(): Observable<ArticleResponse[]> {
    return this.http.get<ArticleResponse[]>(`${this.baseUrl}`)
  }

  getArticle(id: number): Observable<ArticleResponse> {
    return this.http.get<ArticleResponse>(`${this.baseUrl}/${id}`)
  }

  createArticle(body: ArticleRequest) {
    return this.http.post(`${this.baseUrl}`, body)
  }

  getComments(articleId: number): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(`${this.baseUrl}/${articleId}/comments`)
  }

  createComment(articleId: number, body: CommentRequest) {
    return this.http.post(`${this.baseUrl}/${articleId}/comments`, body)
  }
}
