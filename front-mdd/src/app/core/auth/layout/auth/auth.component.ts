import {Component} from '@angular/core';
import {Router, RouterOutlet} from "@angular/router";
import {HeaderComponent} from '../../../../shared/components/header/header.component';
import {Button} from 'primeng/button';
import {Image} from 'primeng/image';

@Component({
  selector: 'app-auth',
  imports: [
    RouterOutlet,
    HeaderComponent,
    Button,
    Image,
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {

  constructor(private router: Router) {
  }

  goHome() {
    this.router.navigate(['/'])
  }

}
