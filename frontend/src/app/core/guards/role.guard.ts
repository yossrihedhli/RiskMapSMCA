import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { UserRole } from '../auth/auth.models';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const roles: unknown = route.data['roles'];

  if (!Array.isArray(roles) || !roles.every(isUserRole)) {
    return router.createUrlTree(['/']);
  }

  const user = authService.currentUser();
  return user !== null && roles.includes(user.role)
    ? true
    : router.createUrlTree(['/']);
};

function isUserRole(value: unknown): value is UserRole {
  return value === 'ADMIN' || value === 'RISK_MANAGER' || value === 'CONSULTANT';
}
