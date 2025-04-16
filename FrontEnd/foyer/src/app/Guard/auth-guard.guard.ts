import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {KeycloakService} from "../Keycloak/keycloak.service";
export const authGuardGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(KeycloakService);
  const router = inject(Router);

  // First check if token exists
  if (!tokenService.keycloak.token) {
    router.navigate(['/login']);
    return false;
  }

  // Then check expiration
  if (tokenService.keycloak.isTokenExpired()) {
    // Attempt to refresh
    tokenService.keycloak.updateToken(30).then(refreshed => {
      if (!refreshed) {
        router.navigate(['/login']);
        return false;
      }
      return true;
    }).catch(() => {
      router.navigate(['/login']);
      return false;
    });
  }

  return true;
};
