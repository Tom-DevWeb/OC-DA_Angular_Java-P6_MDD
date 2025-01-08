import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BearerResponse} from '../models/bearerResponse';
import {LoginRequest} from '../models/loginRequest';
import {UserResponse} from '../models/userResponse';
import {RegisterRequest} from '../models/registerRequest';
import {TokenService} from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl: string = "/api/auth"


  constructor(private http: HttpClient, private tokenService: TokenService) { }



  login(loginRequest: LoginRequest): Observable<BearerResponse> {
    return this.http.post<BearerResponse>(`${this.baseUrl}/login`, loginRequest)
  }

  register(registerRequest: RegisterRequest): Observable<BearerResponse> {
    return this.http.post<BearerResponse>(`${this.baseUrl}/register`, registerRequest)
  }

  refreshToken(refreshToken: string) {
    return this.http.post(`${this.baseUrl}/refresh-token`, {refreshToken})
  }

  authMe(): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/me`)
  }

  disconnect() {
    this.tokenService.removeRefreshToken()
    this.tokenService.removeBearerToken()
    return this.http.post(`${this.baseUrl}/disconnect`, {});
  }
}
