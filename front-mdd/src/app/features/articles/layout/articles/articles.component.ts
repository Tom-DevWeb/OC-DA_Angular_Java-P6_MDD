import { Component } from '@angular/core';
import {HeaderComponent} from '../../../../shared/components/header/header.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-articles',
  imports: [
    HeaderComponent,
    RouterOutlet
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

}
