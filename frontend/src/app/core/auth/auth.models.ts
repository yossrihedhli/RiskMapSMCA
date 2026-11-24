export type UserRole = 'ADMIN' | 'RISK_MANAGER' | 'CONSULTANT';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthUser {
  id: number;
  fullName: string;
  email: string;
  role: UserRole;
}

export interface LoginResponse {
  token: string;
  user: AuthUser;
}
