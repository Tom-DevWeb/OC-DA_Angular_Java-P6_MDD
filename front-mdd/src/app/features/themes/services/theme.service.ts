import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ThemeResponse} from '../models/themeResponse';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private baseUrl: string = "/api/themes"


  constructor(private http: HttpClient) { }

  getThemes(): Observable<ThemeResponse[]> {
    return this.http.get<ThemeResponse[]>(`${this.baseUrl}`)
  }

  getThemeUser(): Observable<ThemeResponse[]> {
    return this.http.get<ThemeResponse[]>(`${this.baseUrl}/subscriptions`)
  }

  subscribeTheme(themeId: number, userId: number) {
    return this.http.post(`${this.baseUrl}/${themeId}/users/${userId}`, {})
  }

  unsubscribeTheme(themeId: number, userId: number) {
    return this.http.delete(`${this.baseUrl}/${themeId}/users/${userId}`)
  }
}
