import { Component } from '@angular/core';
import {Image} from 'primeng/image';
import {TokenService} from '../../../core/auth/services/token.service';
import {AsyncPipe, CommonModule} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {Avatar} from 'primeng/avatar';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [
    Image,
    AsyncPipe,
    RouterLink,
    Avatar,
    CommonModule,
    RouterLinkActive,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  isLogged$: Observable<boolean>;

  constructor(private tokenService: TokenService) {
    this.isLogged$ = this.tokenService.currentUser$
  }



}
