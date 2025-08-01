import { Component, Input, OnInit } from '@angular/core';
import { VendaService } from '../venda.service';
import { Venda } from '../venda.model';
import { Router } from '@angular/router';

import { ClienteService } from '../../cliente/cliente.service';
import { FormaPagamentoService } from '../../formaPagamento/formaPagamento.service';
import { ProductService } from '../../product/product.service';

import { Cliente } from '../../cliente/cliente.model';
import { FormaPagamento } from '../../formaPagamento/formaPagamento.model';
import { Product } from '../../product/product.model';

@Component({
  selector: 'app-venda-read',
  templateUrl: './venda-read.component.html',
  styleUrls: ['./venda-read.component.css']
})
export class VendaReadComponent implements OnInit {

  @Input() vendas: Venda[] = [];

  clientesMap = new Map<number, Cliente>();
  formasPagamentoMap = new Map<number, FormaPagamento>();
  productsMap = new Map<number, Product>();

  displayedColumns = [
  'vendaCodigo',
  'cliNome',
  'vendaData',
  'vendaValorTotal',
  'formaPagamento',
  'quantidadeItens'
  ];

  loading = false;

  constructor(
    private vendaService: VendaService,
    private clienteService: ClienteService,
    private formaPagamentoService: FormaPagamentoService,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadVendas();
    this.loadClientes();
    this.loadFormasPagamento();
    this.loadProdutos();
  }

  loadVendas(): void {
    this.loading = true;
    this.vendaService.read().subscribe({
      next: (data: Venda[]) => {
        this.vendas = data;
        this.loading = false;
      },
      error: (err: any) => {
        console.error('Erro ao carregar vendas', err);
        this.vendaService.showMessage('Erro ao carregar vendas');
        this.loading = false;
      }
    });
  }

  loadClientes(): void {
    this.clienteService.readClientes().subscribe({
      next: (clientes: Cliente[]) => {
        clientes.forEach((c: Cliente) => this.clientesMap.set(c.cliId!, c));
      },
      error: (err: any) => console.error('Erro ao carregar clientes', err)
    });
  }

  loadFormasPagamento(): void {
    this.formaPagamentoService.read().subscribe({
      next: (formas: FormaPagamento[]) => {
        formas.forEach((f: FormaPagamento) => this.formasPagamentoMap.set(f.fpgId!, f));
      },
      error: (err: any) => console.error('Erro ao carregar formas de pagamento', err)
    });
  }

  loadProdutos(): void {
    this.productService.read().subscribe({
      next: (prods: Product[]) => {
        prods.forEach((p: Product) => this.productsMap.set(p.proId!, p));
      },
      error: (err: any) => console.error('Erro ao carregar produtos', err)
    });
  }

  getClienteNome(cliId: number): string {
    return this.clientesMap.get(cliId)?.cliNome || 'Cliente não encontrado';
  }

  getFormaPagamentoDescricao(fpgId: number): string {
    const forma = this.formasPagamentoMap.get(fpgId);
    return forma ? `${forma.fpgTipo} - ${forma.fpgDescricao}` : 'Forma pagamento não encontrada';
  }

  getProdutosResumo(compras: Venda['compras']): string {
    if (!compras || compras.length === 0) return 'Sem produtos';
    return compras.map(c => {
      const produto = this.productsMap.get(c.proId!);
      const nome = produto ? produto.proNome : 'Produto não encontrado';
      return `${nome} (${c.compraQuantidade} x R$${c.compraPrecoVenda.toFixed(2)})`;
    }).join(', ');
  }

  onInfoClick(venda: Venda): void {
    console.log('Informações da venda:', venda);
    this.vendaService.showMessage(`Venda ${venda.vendaCodigo} clicada.`);
    // Exemplo de navegação para página detalhes da venda:
    // this.router.navigate(['/vendas', venda.vendaId]);
  }
}