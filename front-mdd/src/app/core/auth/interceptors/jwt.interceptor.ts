import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {TokenService} from '../services/token.service';

@Injectable({providedIn: 'root'})
export class JwtInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let bearerToken = this.tokenService.getBearerToken();

    //Intercepte les requêtes sortantes et insère un bearer token
    if (bearerToken) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${bearerToken}`
        }
      })
      return next.handle(authReq)
    } else { // S'il n'y a pas de currentUser et accessToken il envoie la req sans bearer
      return next.handle(req);
    }
  }
}
