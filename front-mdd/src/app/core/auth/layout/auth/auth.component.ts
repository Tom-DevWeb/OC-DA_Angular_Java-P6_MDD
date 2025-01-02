import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {HeaderComponent} from '../../../../shared/components/header/header.component';
import {Button} from 'primeng/button';
import {Location} from '@angular/common';
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

  constructor(private location: Location) {
  }

  goBack() {
    this.location.back();
  }

}
