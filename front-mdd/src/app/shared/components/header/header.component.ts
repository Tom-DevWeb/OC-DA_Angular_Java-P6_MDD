import {Component} from '@angular/core';
import {Image} from 'primeng/image';
import {TokenService} from '../../../core/auth/services/token.service';
import {AsyncPipe, CommonModule} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {Observable} from 'rxjs';
import {Button} from 'primeng/button';
import {Drawer} from 'primeng/drawer';

@Component({
  selector: 'app-header',
  imports: [
    Image,
    AsyncPipe,
    RouterLink,
    CommonModule,
    RouterLinkActive,
    Button,
    Drawer,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  isLogged$: Observable<boolean>;

  visible: boolean = false;

  constructor(private tokenService: TokenService) {
    this.isLogged$ = this.tokenService.currentUser$
  }



}
