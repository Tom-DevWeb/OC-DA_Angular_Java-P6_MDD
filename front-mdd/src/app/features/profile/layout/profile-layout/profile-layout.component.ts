import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from '../../../../shared/components/header/header.component';
import {RouterOutlet} from '@angular/router';
import {ThemeResponse} from '../../../themes/models/themeResponse';
import {ThemeService} from '../../../themes/services/theme.service';
import {AuthService} from '../../../../core/auth/services/auth.service';
import {Button} from 'primeng/button';
import {DataView} from 'primeng/dataview';
import {NgForOf, NgIf, TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-profile-layout',
  imports: [
    HeaderComponent,
    RouterOutlet,
    Button,
    DataView,
    NgForOf,
    NgIf,
    TitleCasePipe
  ],
  templateUrl: './profile-layout.component.html',
  styleUrl: './profile-layout.component.scss'
})
export class ProfileLayoutComponent implements OnInit {

  public themes!: ThemeResponse[]
  public subscribedThemes: number[] = []
  public userId!: number

  constructor(private themeService: ThemeService, private authService: AuthService) {}

  ngOnInit() {
    this.getUserId()
  }

  getUserId() {
    return this.authService.authMe().subscribe(user => {
      this.userId = user.id
      this.getUserSubscriptions()
    })
  }

  getUserSubscriptions() {
    this.themeService.getThemeUser().subscribe({
      next: (response: ThemeResponse[]) => {
        this.themes = response
        this.subscribedThemes = response.map(theme => theme.id);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  isSubscribed(themeId: number): boolean {
    return this.subscribedThemes && this.subscribedThemes.includes(themeId);
  }

  toggleSubscription(themeId: number) {

      this.themeService.unsubscribeTheme(themeId, this.userId).subscribe({
        next: () => {
          this.getUserSubscriptions()
        },
        error: (err) => {
          console.error(err);
        }
      });

  }
}
