import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cliente } from 'src/app/component/cliente/cliente.model';
import { ClienteService } from 'src/app/component/cliente/cliente.service';

@Component({
  selector: 'app-cliente-crud', // Define o seletor do componente
  templateUrl: './cliente-crud.component.html', // Caminho para o template HTML
  styleUrls: ['./cliente-crud.component.css'] // Caminho para o arquivo de estilos CSS
})

export class ClienteCrudComponent implements OnInit {
  searchTerm: string = '';
  allClientes: Cliente[] = [];
  filteredClientes: Cliente[] = [];

  constructor(
    private router: Router,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.clienteService.readClientes().subscribe(clientes => {
      this.allClientes = clientes;
      this.filteredClientes = clientes;
    });
  }

  navigateToClienteCreate(): void {
    this.router.navigate(['/clientes/create']);
  }

  filterClientes(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredClientes = this.allClientes.filter(c =>
      c.cliNome.toLowerCase().includes(term) ||
      c.cliCpf.toLowerCase().includes(term) ||
      c.conEmail.toLowerCase().includes(term) ||
      c.conCelular.toLowerCase().includes(term) ||
      c.conTelefoneComercial.toLowerCase().includes(term)
    );
  }
}