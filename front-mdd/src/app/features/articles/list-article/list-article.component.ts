import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../core/auth/services/auth.service';

@Component({
  selector: 'app-list-article',
  imports: [],
  templateUrl: './list-article.component.html',
  styleUrl: './list-article.component.scss'
})
export class ListArticleComponent implements OnInit {

  myName: string = ''

  constructor(private authService: AuthService) {
  }


  ngOnInit() {
    this.authme()
  }

  authme() {
    this.authService.authMe().subscribe({
      next: (response) => {
        this.myName = response.username;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }



}
