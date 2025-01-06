import {Component, OnInit} from '@angular/core';
import {ArticleService} from '../../services/article.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ArticleResponse} from '../../models/articleResponse';
import {DatePipe, Location, NgFor, NgIf, TitleCasePipe} from '@angular/common';
import {CommentResponse} from '../../models/commentResponse';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommentRequest} from '../../models/commentRequest';
import {AuthService} from '../../../../core/auth/services/auth.service';
import {Button} from 'primeng/button';
import {Message} from 'primeng/message';
import {Textarea} from 'primeng/textarea';

@Component({
  selector: 'app-content-article',
  imports: [
    TitleCasePipe,
    NgFor,
    NgIf,
    FormsModule,
    Button,
    Message,
    ReactiveFormsModule,
    Textarea,
    DatePipe
  ],
  templateUrl: './content-article.component.html',
  styleUrl: './content-article.component.scss'
})
export class ContentArticleComponent implements OnInit {

  public article!: ArticleResponse
  public comments!: CommentResponse[]

  formGroup!: FormGroup;
  errorMessage: string | null = null;

  public userId!: number

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private location: Location
  ) {}

  ngOnInit() {
    const articleId = this.route.snapshot.params['articleId'];
    this.getArticles(articleId)
    this.getComments(articleId)
    this.initFormControls()
    this.getUserId()
  }

  getArticles(articleId: number) {
    this.articleService.getArticle(articleId).subscribe({
      next: article => {
        this.article = article;
      },
      error: err => {
        this.router.navigate(['/articles']);
        console.error(err);
      }
    })
  }

  getComments(articleId: number) {
    this.articleService.getComments(articleId).subscribe({
      next: comments => {
        this.comments = comments
      },
      error: err => {
        console.log(err);
      }
    })
  }

  getUserId() {
    return this.authService.authMe().subscribe(user => {
      this.userId = user.id
    })
  }

  initFormControls() {
    this.formGroup = this.formBuilder.group({
      content: ['', [Validators.required]]
    })
  }

  onSubmit() {
    if (this.formGroup.valid && this.userId) {
      const commentRequest: CommentRequest = {
        content: this.formGroup.value.content,
        author: {
          id: this.userId,
        }
      }
      this.articleService.createComment(this.article.id, commentRequest).subscribe({
          next: () => {
            this.getComments(this.article.id)
            this.formGroup.reset()
          },
          error: (err) => {
            console.error(err);
            this.errorMessage = err.message;
          }
        }
      )
    }
  }

  goBack() {
    this.location.back();
  }

}
