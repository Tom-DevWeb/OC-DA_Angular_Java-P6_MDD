import {HttpInterceptor} from '@angular/common/http';
import {AuthService} from '../auth.service';

export class JwtInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }
}
