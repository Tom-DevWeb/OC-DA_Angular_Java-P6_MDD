import {ApplicationConfig, LOCALE_ID, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {providePrimeNG} from 'primeng/config';
import {customTheme} from '../themes/customTheme';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {TokenInterceptor} from './core/auth/interceptors/token.interceptor';
import {DATE_PIPE_DEFAULT_OPTIONS, registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';

registerLocaleData(localeFr)

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: customTheme,
        options: {
          prefix: 'p',
          darkModeSelector: 'light',

        }
      }
    }),
    provideHttpClient(withInterceptorsFromDi()),
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'mediumDate'}},
    {provide: LOCALE_ID, useValue: 'fr-FR'}
  ]
};
