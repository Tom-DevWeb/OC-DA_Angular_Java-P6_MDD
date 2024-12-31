import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BearerResponse} from '../models/bearerResponse';
import {LoginRequest} from '../models/loginRequest';
import {UserResponse} from '../models/userResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl: string = "/api/auth"


  constructor(private http: HttpClient) { }



  login(loginRequest: LoginRequest): Observable<BearerResponse> {
    return this.http.post<BearerResponse>(`${this.baseUrl}/login`, loginRequest)
  }

  register(email: string, password: string, username: string) {
    return this.http.post(`${this.baseUrl}/register`, {email, password, username})
  }

  refreshToken(refreshToken: string) {
    return this.http.post(`${this.baseUrl}/refresh-token`, {refreshToken})
  }

  authMe(): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.baseUrl}/me`)
  }

  disconnect() {
    return this.http.post(`${this.baseUrl}/disconnect`, {});
  }
}
