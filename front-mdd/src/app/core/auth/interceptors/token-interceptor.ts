import {Injectable} from '@angular/core';
import {HttpInterceptor} from '@angular/common/http';
import {AuthService} from '../auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }
}
