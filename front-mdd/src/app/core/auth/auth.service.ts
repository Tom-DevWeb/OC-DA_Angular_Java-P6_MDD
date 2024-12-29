import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BearerResponse} from './models/bearerResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  private baseUrl: string = "/auth"

  login(email: string, password: string): Observable<HttpResponse<BearerResponse>> {
    return this.http.post<HttpResponse<BearerResponse>>(`${this.baseUrl}/login`, {email, password})
  }

  register(email: string, password: string, username: string) {
    return this.http.post(`${this.baseUrl}/register`, {email, password, username})
  }
}
