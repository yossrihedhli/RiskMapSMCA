import { HttpClient } from '@angular/common/http';
import { computed, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthUser, LoginRequest, LoginResponse, UserRole } from './auth.models';

const TOKEN_KEY = 'riskmap.jwt';
const USER_KEY = 'riskmap.user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly user = signal<AuthUser | null>(this.readStoredUser());
  readonly currentUser = this.user.asReadonly();
  readonly isAuthenticated = computed(() => this.getToken() !== null && this.user() !== null);
  readonly canWrite = computed(() => {
    const role = this.currentUser()?.role;
    return role === 'ADMIN' || role === 'RISK_MANAGER';
  });

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        tap((response) => {
          sessionStorage.setItem(TOKEN_KEY, response.token);
          sessionStorage.setItem(USER_KEY, JSON.stringify(response.user));
          this.user.set(response.user);
        })
      );
  }

  getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  logout(): void {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(USER_KEY);
    this.user.set(null);
    void this.router.navigate(['/login']);
  }

  hasRole(role: UserRole): boolean {
    return this.currentUser()?.role === role;
  }

  private readStoredUser(): AuthUser | null {
    const token = sessionStorage.getItem(TOKEN_KEY);
    const storedUser = sessionStorage.getItem(USER_KEY);
    if (token === null || storedUser === null) {
      sessionStorage.removeItem(TOKEN_KEY);
      sessionStorage.removeItem(USER_KEY);
      return null;
    }

    try {
      const user: unknown = JSON.parse(storedUser);
      if (isAuthUser(user)) {
        return user;
      }
    } catch {}

    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(USER_KEY);
    return null;
  }
}

function isAuthUser(value: unknown): value is AuthUser {
  if (typeof value !== 'object' || value === null) {
    return false;
  }

  const user = value as Record<string, unknown>;
  return (
    typeof user['id'] === 'number' &&
    typeof user['fullName'] === 'string' &&
    typeof user['email'] === 'string' &&
    isUserRole(user['role'])
  );
}

function isUserRole(value: unknown): value is UserRole {
  return value === 'ADMIN' || value === 'RISK_MANAGER' || value === 'CONSULTANT';
}
