import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {TokenService} from '../services/token.service';
import {map} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private tokenService: TokenService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.tokenService.currentUser$.pipe(
      map(isConnected => {
        if (isConnected) {
          return true
        } else {
          this.router.navigate(['/auth/login']);
          return false;
        }
      })
    )
  }
}
