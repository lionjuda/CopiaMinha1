import { Component } from '@angular/core';
import { Cliente } from '../cliente.model';
import { ClienteService } from '../cliente.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-cliente-delete',
  templateUrl: './cliente-delete.component.html',
  styleUrls: ['./cliente-delete.component.css']
})
export class ClienteDeleteComponent {
  cliente!: Cliente;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const cliId = this.route.snapshot.paramMap.get('cliId');
    if (cliId) {
      this.clienteService.readClienteById(cliId).subscribe((cliente: Cliente) => {
        this.cliente = cliente;
      });
    } else {
      this.router.navigate(['/clientes']);
    }
  }

  deleteCliente(): void {
    if (this.cliente.cliId) {
      this.clienteService.deleteCliente(this.cliente.cliId).subscribe(() => {
        this.clienteService.showMessage('Cliente excluído com sucesso!');
        this.router.navigate(['/clientes']);
      });
    }
  }

  cancel(): void{
    this.router.navigate(['/clientes'])
  }
}