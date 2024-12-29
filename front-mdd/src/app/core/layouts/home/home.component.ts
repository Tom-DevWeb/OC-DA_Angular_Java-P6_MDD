import {Component} from '@angular/core';
import {Image} from 'primeng/image';
import {RouterLink} from '@angular/router';
import {ButtonModule} from 'primeng/button';

@Component({
  selector: 'app-home',
  imports: [
    Image,
    RouterLink,
    ButtonModule,

  ],
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
