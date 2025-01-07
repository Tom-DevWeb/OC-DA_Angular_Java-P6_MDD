import {Component, OnInit} from '@angular/core';
import {Button} from "primeng/button";
import {InputText} from "primeng/inputtext";
import {Message} from "primeng/message";
import {Location, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from '../../../../core/auth/services/auth.service';
import {Router} from '@angular/router';
import {ArticleService} from '../../services/article.service';
import {ArticleRequest} from '../../models/articleRequest';
import {ThemeService} from '../../../themes/services/theme.service';
import {Textarea} from 'primeng/textarea';
import {DropdownModule} from 'primeng/dropdown';

@Component({
  selector: 'app-create-article',
  imports: [
    Button,
    InputText,
    Message,
    NgIf,
    ReactiveFormsModule,
    Textarea,
    DropdownModule
  ],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss'
})
export class CreateArticleComponent implements OnInit {

  formGroup!: FormGroup;
  errorMessage: string | null = null;

  public userId!: number
  public themes: { id: number, title: string }[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private articleService: ArticleService,
    private authService: AuthService,
    private themeService: ThemeService,
    private location: Location,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.initFormControls()
    this.getThemes()
    this.getUserId()
  }

  initFormControls() {
    this.formGroup = this.formBuilder.group({
      theme: [null, Validators.required],
      title: ['', [Validators.required]],
      content: ['', Validators.required],
    })
  }

  getUserId() {
    return this.authService.authMe().subscribe(user => {
      this.userId = user.id
    })
  }

  getThemes() {
    return this.themeService.getThemes().subscribe(themes => {
      this.themes = themes.map(theme => ({ id: theme.id, title: theme.title }));
    })
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const articleRequest: ArticleRequest = {
        title: this.formGroup.value.title,
        content: this.formGroup.value.content,
        theme: {
          id: this.formGroup.value.theme
        },
        author: {
          id: this.userId,
        }
      }
      console.log(articleRequest)
      this.articleService.createArticle(articleRequest).subscribe({
          next: () => {
            this.router.navigate(['/articles']);
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
