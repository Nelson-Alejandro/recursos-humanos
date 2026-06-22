import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../core/services/empleado.service';
import { AuthService } from '../../core/services/auth.service';
import { Empleado } from '../../shared/models/empleado.model';

@Component({
  selector: 'app-empleados',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './empleados-list.component.html',
  styleUrls: ['./empleados-list.component.css']
})
export class EmpleadosListComponent implements OnInit {
  empleados: Empleado[] = [];
  filtered: Empleado[] = [];
  search = '';
  loading = true;
  error = '';
  confirmDeleteId: number | null = null;
  username = '';
  isAdmin = false;

  constructor(
    private empleadoService: EmpleadoService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const user = this.auth.getUser();
    this.username = user?.username || '';
    this.isAdmin = this.auth.isAdmin();
    this.loadEmpleados();
  }

  loadEmpleados(): void {
    this.loading = true;
    this.empleadoService.getAll().subscribe({
      next: data => {
        this.empleados = data;
        this.applyFilter();
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar empleados.';
        this.loading = false;
      }
    });
  }

  applyFilter(): void {
    const q = this.search.toLowerCase();
    this.filtered = this.empleados.filter(e =>
      e.nombres.toLowerCase().includes(q) ||
      e.apellidos.toLowerCase().includes(q) ||
      e.email.toLowerCase().includes(q) ||
      e.cargo.toLowerCase().includes(q) ||
      e.departamento.toLowerCase().includes(q)
    );
  }

  confirmDelete(id: number): void {
    this.confirmDeleteId = id;
  }

  cancelDelete(): void {
    this.confirmDeleteId = null;
  }

  deleteEmpleado(): void {
    if (this.confirmDeleteId === null) return;
    this.empleadoService.delete(this.confirmDeleteId).subscribe({
      next: () => {
        this.empleados = this.empleados.filter(e => e.id !== this.confirmDeleteId);
        this.applyFilter();
        this.confirmDeleteId = null;
      },
      error: () => { this.error = 'Error al eliminar empleado.'; this.confirmDeleteId = null; }
    });
  }

  logout(): void { this.auth.logout(); }

  editEmpleado(id: number): void { this.router.navigate(['/empleados/editar', id]); }

  nuevoEmpleado(): void { this.router.navigate(['/empleados/nuevo']); }
  irAUsuarios(): void { this.router.navigate(['/usuarios']); }
}
