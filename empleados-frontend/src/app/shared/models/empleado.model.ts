export interface Empleado {
  id?: number;
  nombres: string;
  apellidos: string;
  email: string;
  telefono?: string;
  cargo: string;
  departamento: string;
  salario: number;
  fechaContratacion: string;
  activo?: boolean;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  rol: string;
}

export interface Usuario {
  id?: number;
  username: string;
  password?: string;
  rol: string;
  activo?: boolean;
}

export interface UsuarioRequest {
  username: string;
  password?: string;
  rol: string;
}
