import {Component} from '@angular/core';
import {HeaderComponent} from '../../../../shared/components/header/header.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-profile-layout',
  imports: [
    HeaderComponent,
    RouterOutlet
  ],
  templateUrl: './profile-layout.component.html',
  styleUrl: './profile-layout.component.scss'
})
export class ProfileLayoutComponent {
}
