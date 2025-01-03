import { Component } from '@angular/core';
import {HeaderComponent} from '../../components/header/header.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-articles',
  imports: [
    HeaderComponent,
    RouterOutlet
  ],
  templateUrl: './header-layout.component.html',
  styleUrl: './header-layout.component.scss'
})
export class HeaderLayoutComponent {

}
