import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError} from 'rxjs';
import {TokenService} from '../services/token.service';
import {Router} from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private isRefreshing = false;

  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null)

  constructor(
    private readonly authService: AuthService,
    private readonly tokenService: TokenService,
    private readonly router: Router,
    ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let bearerToken = this.tokenService.getBearerToken();
    if (bearerToken) {
      req = this.addToken(req, bearerToken);
    }
    return next.handle(req).pipe(
      catchError(err => {
        if (err instanceof HttpErrorResponse && err.status === 401) {
          return this.handle401Error(req, next)
        } else {
          return throwError(err);
        }
      })
    )
  }

  private addToken(req: HttpRequest<any>, token: string) {
    console.log("TokenInterceptor")
    return req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    })
  }

  //FIXME Erreur sur la gestion du refresh token et de son utilisation
  private handle401Error(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.tokenService.getRefreshToken() === null) {
      this.router.navigate(['/login']);
      return throwError(() => new Error("Refresh token is missing"))
    } else if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      // <string> car getRefreshToken est string ou null mais dans la condition avant on prend que si non null
      let refreshToken: string = <string>this.tokenService.getRefreshToken();
      return this.authService.refreshToken(refreshToken).pipe(
        switchMap((user: any) => {
          console.log("Refresh token is refreshed");
          this.isRefreshing = false
          this.refreshTokenSubject.next(user.accessToken)
          return next.handle(this.addToken(req, user.accessToken));
        }
      ),
        catchError((err) => {
          console.log("Refresh token isn't refreshed");
          this.isRefreshing = false
          this.authService.disconnect()
          return throwError(err);
        })
      )
    } else {
      console.log("Refresh token else");
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(accessToken => {
          return next.handle(this.addToken(req, accessToken))
        })
      )

    }
  }
}
