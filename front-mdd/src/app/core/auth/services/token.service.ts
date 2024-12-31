import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {BearerResponse} from '../models/bearerResponse';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private currentUserSubject: BehaviorSubject<boolean>
  public currentUser$: Observable<boolean>

  private bearerTokenKey: string = 'bearerToken'
  private refreshTokenKey: string = 'refreshToken'

  constructor() {
    const isAuthenticated = !!this.getBearerToken()
    this.currentUserSubject = new BehaviorSubject<boolean>(isAuthenticated)
    this.currentUser$ = this.currentUserSubject.asObservable()
  }

  setTokens(tokens: BearerResponse) {
    localStorage.setItem(this.refreshTokenKey, JSON.stringify(tokens.refresh))
    sessionStorage.setItem(this.bearerTokenKey, JSON.stringify(tokens.bearer))
    this.currentUserSubject.next(true)
  }

  getBearerToken(): string | null {
    const bearerTokenString: string | null = sessionStorage.getItem(this.bearerTokenKey)
    return bearerTokenString ? JSON.parse(bearerTokenString) : null
  }

  getRefreshToken(): string | null {
    const refreshTokenString: string | null = localStorage.getItem(this.refreshTokenKey)
    return refreshTokenString ? JSON.parse(refreshTokenString) : null
  }

  removeBearerToken(): void {
    sessionStorage.removeItem(this.bearerTokenKey)
    this.currentUserSubject.next(false)
  }
}
