import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Venda } from 'src/app/component/venda/venda.model';
import { VendaService } from 'src/app/component/venda/venda.service';
import { Cliente } from 'src/app/component/cliente/cliente.model';
import { ClienteService } from 'src/app/component/cliente/cliente.service';

@Component({
  selector: 'app-venda-crud',
  templateUrl: './venda-crud.component.html',
  styleUrls: ['./venda-crud.component.css']
})
export class VendaCrudComponent implements OnInit {

  allVenda: Venda[] = [];
  vendaFilter: Venda[] = [];
  searchTerm: string = '';

  clientesMap = new Map<number, Cliente>();

  constructor(
    private router: Router,
    private vendaService: VendaService,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    // Carrega todas as vendas
    this.vendaService.read().subscribe((venda: Venda[]) => {
      this.allVenda = venda;
      this.vendaFilter = venda; // mostra tudo inicialmente
    });

    // Carrega todos os clientes para o Map
    this.clienteService.readClientes().subscribe({
      next: (clientes: Cliente[]) => {
        clientes.forEach(c => this.clientesMap.set(c.cliId!, c));
      },
      error: () => {
        console.error('Erro ao carregar clientes');
      }
    });
  }

  navigateToVendaCreate(): void {
    this.router.navigate(['/vendas/create']);
  }

  filterVendas(): void {
    const filter = this.searchTerm.toLowerCase();

    this.vendaFilter = this.allVenda.filter(v => {
      const clienteNome = this.clientesMap.get(v.cliId)?.cliNome?.toLowerCase() || '';
      const vendaCodigo = v.vendaCodigo?.toString().toLowerCase() || '';
      const cliId = v.cliId?.toString().toLowerCase() || '';

      return (
        clienteNome.includes(filter) ||
        vendaCodigo.includes(filter) ||
        cliId.includes(filter)
      );
    });
  }
}