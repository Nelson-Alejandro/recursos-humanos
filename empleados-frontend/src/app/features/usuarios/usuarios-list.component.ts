import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../core/services/usuario.service';
import { AuthService } from '../../core/services/auth.service';
import { Usuario } from '../../shared/models/empleado.model';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuarios-list.component.html',
  styleUrls: ['./usuarios-list.component.css']
})
export class UsuariosListComponent implements OnInit {
  usuarios: Usuario[] = [];
  loading = true;
  error = '';
  confirmDeleteId: number | null = null;
  username = '';

  constructor(
    private usuarioService: UsuarioService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.username = this.auth.getUser()?.username || '';
    this.loadUsuarios();
  }

  loadUsuarios(): void {
    this.loading = true;
    this.usuarioService.getAll().subscribe({
      next: data => { this.usuarios = data; this.loading = false; },
      error: () => { this.error = 'Error al cargar usuarios.'; this.loading = false; }
    });
  }

  confirmDelete(id: number): void { this.confirmDeleteId = id; }
  cancelDelete(): void { this.confirmDeleteId = null; }

  deleteUsuario(): void {
    if (this.confirmDeleteId === null) return;
    this.usuarioService.delete(this.confirmDeleteId).subscribe({
      next: () => {
        this.usuarios = this.usuarios.filter(u => u.id !== this.confirmDeleteId);
        this.confirmDeleteId = null;
      },
      error: () => { this.error = 'Error al eliminar usuario.'; this.confirmDeleteId = null; }
    });
  }

  logout(): void { this.auth.logout(); }
  editUsuario(id: number): void { this.router.navigate(['/usuarios/editar', id]); }
  nuevoUsuario(): void { this.router.navigate(['/usuarios/nuevo']); }
  irAEmpleados(): void { this.router.navigate(['/empleados']); }
}
