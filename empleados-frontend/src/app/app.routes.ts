import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'empleados', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'empleados',
    canActivate: [authGuard],
    loadComponent: () => import('./features/empleados/empleados-list.component').then(m => m.EmpleadosListComponent)
  },
  {
    path: 'empleados/nuevo',
    canActivate: [authGuard],
    loadComponent: () => import('./features/empleados/empleado-form.component').then(m => m.EmpleadoFormComponent)
  },
  {
    path: 'empleados/editar/:id',
    canActivate: [authGuard],
    loadComponent: () => import('./features/empleados/empleado-form.component').then(m => m.EmpleadoFormComponent)
  },
  {
    path: 'usuarios',
    canActivate: [authGuard],
    loadComponent: () => import('./features/usuarios/usuarios-list.component').then(m => m.UsuariosListComponent)
  },
  {
    path: 'usuarios/nuevo',
    canActivate: [authGuard],
    loadComponent: () => import('./features/usuarios/usuario-form.component').then(m => m.UsuarioFormComponent)
  },
  {
    path: 'usuarios/editar/:id',
    canActivate: [authGuard],
    loadComponent: () => import('./features/usuarios/usuario-form.component').then(m => m.UsuarioFormComponent)
  },
  { path: '**', redirectTo: 'empleados' },
];
