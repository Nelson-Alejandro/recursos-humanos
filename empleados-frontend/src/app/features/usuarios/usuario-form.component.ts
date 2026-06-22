import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../core/services/usuario.service';
import { UsuarioRequest } from '../../shared/models/empleado.model';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {
  isEdit = false;
  usuarioId: number | null = null;
  loading = false;
  loadingData = false;
  showPassword = false;
  error = '';
  success = '';

  usuario: UsuarioRequest = { username: '', password: '', rol: 'RRHH' };

  constructor(
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.usuarioId = +id;
      this.loadingData = true;
      this.usuarioService.getAll().subscribe({
        next: lista => {
          const found = lista.find(u => u.id === this.usuarioId);
          if (found) {
            this.usuario = { username: found.username, password: '', rol: found.rol };
          }
          this.loadingData = false;
        },
        error: () => { this.error = 'Error al cargar el usuario.'; this.loadingData = false; }
      });
    }
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';
    this.success = '';

    const payload: UsuarioRequest = { ...this.usuario };
    if (this.isEdit && !payload.password) {
      delete payload.password;
    }

    const op = this.isEdit
      ? this.usuarioService.update(this.usuarioId!, payload)
      : this.usuarioService.create(payload);

    op.subscribe({
      next: () => {
        this.success = this.isEdit ? 'Usuario actualizado correctamente.' : 'Usuario RRHH creado correctamente.';
        this.loading = false;
        setTimeout(() => this.router.navigate(['/usuarios']), 1200);
      },
      error: (err) => {
        this.error = err?.error?.message || 'Ocurrió un error. Verifica los datos.';
        this.loading = false;
      }
    });
  }

  cancelar(): void { this.router.navigate(['/usuarios']); }
}
