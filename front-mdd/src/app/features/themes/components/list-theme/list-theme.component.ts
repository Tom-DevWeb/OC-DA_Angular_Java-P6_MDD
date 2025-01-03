import {Component, OnInit} from '@angular/core';
import {ThemeResponse} from '../../models/themeResponse';
import {ThemeService} from '../../services/theme.service';
import {NgForOf, NgIf, TitleCasePipe} from '@angular/common';
import {Button} from 'primeng/button';
import {DataView} from 'primeng/dataview';
import {AuthService} from '../../../../core/auth/services/auth.service';

@Component({
  selector: 'app-list-theme',
  imports: [
    Button,
    DataView,
    NgForOf,
    NgIf,
    TitleCasePipe
  ],
  templateUrl: './list-theme.component.html',
  styleUrl: './list-theme.component.scss'
})
export class ListThemeComponent implements OnInit {

  public themes!: ThemeResponse[]
  public subscribedThemes: number[] = []
  public userId!: number

  constructor(private themeService: ThemeService, private authService: AuthService) {}

  ngOnInit() {
    this.getThemes()
    this.getUserId()
  }

  getUserId() {
    return this.authService.authMe().subscribe(user => {
      this.userId = user.id
      this.getUserSubscriptions()
    })
  }

  getThemes() {
    this.themeService.getThemes().subscribe({
      next: (response: ThemeResponse[]) => {
        this.themes = response
      },
      error: (error) => {
        console.error(error)
      }
    })
  }

  getUserSubscriptions() {
      this.themeService.getThemeUser().subscribe({
        next: (response: ThemeResponse[]) => {
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
    if (this.userId && this.isSubscribed(themeId)) {
      this.themeService.unsubscribeTheme(themeId, this.userId).subscribe(() => {
        this.subscribedThemes = this.subscribedThemes.filter(id => id !== themeId);
      });
    } else {
      this.themeService.subscribeTheme(themeId, this.userId).subscribe(() => {
        this.subscribedThemes.push(themeId);
      });
    }
  }
}
