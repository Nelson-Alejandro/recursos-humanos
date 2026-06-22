import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { EmpleadoService } from '../../core/services/empleado.service';
import { Empleado } from '../../shared/models/empleado.model';

@Component({
  selector: 'app-empleado-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './empleado-form.component.html',
  styleUrls: ['./empleado-form.component.css']
})
export class EmpleadoFormComponent implements OnInit {
  isEdit = false;
  empleadoId: number | null = null;
  loading = false;
  loadingData = false;
  error = '';
  success = '';

  empleado: Empleado = {
    nombres: '', apellidos: '', email: '', telefono: '',
    cargo: '', departamento: '', salario: 0,
    fechaContratacion: '', activo: true
  };

  departamentos = ['Tecnología', 'Recursos Humanos', 'Finanzas', 'Marketing', 'Operaciones', 'Ventas', 'Legal'];

  constructor(
    private empleadoService: EmpleadoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.empleadoId = +id;
      this.loadingData = true;
      this.empleadoService.getById(this.empleadoId).subscribe({
        next: data => {
          this.empleado = { ...data };
          this.loadingData = false;
        },
        error: () => {
          this.error = 'Error al cargar el empleado.';
          this.loadingData = false;
        }
      });
    }
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';
    this.success = '';

    const op = this.isEdit
      ? this.empleadoService.update(this.empleadoId!, this.empleado)
      : this.empleadoService.create(this.empleado);

    op.subscribe({
      next: () => {
        this.success = this.isEdit ? 'Empleado actualizado correctamente.' : 'Empleado creado correctamente.';
        this.loading = false;
        setTimeout(() => this.router.navigate(['/empleados']), 1200);
      },
      error: (err) => {
        this.error = err?.error?.message || 'Ocurrió un error. Verifica los datos.';
        this.loading = false;
      }
    });
  }

  cancelar(): void { this.router.navigate(['/empleados']); }
}
