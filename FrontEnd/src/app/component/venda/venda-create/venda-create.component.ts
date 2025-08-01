import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

import { Product } from '../../product/product.model';
import { ProductService } from '../../product/product.service';
import { VendaService } from '../venda.service';
import { Venda, Compra } from '../venda.model';

import { Cliente } from '../../cliente/cliente.model';
import { ClienteService } from '../../cliente/cliente.service';

import { FormaPagamento } from '../../formaPagamento/formaPagamento.model';
import { FormaPagamentoService } from '../../formaPagamento/formaPagamento.service';

@Component({
  selector: 'app-venda-create',
  templateUrl: './venda-create.component.html',
  styleUrls: ['./venda-create.component.css']
})
export class VendaCreateComponent implements OnInit {

  vendaForm!: FormGroup;
  products: Product[] = [];
  clientes: Cliente[] = [];
  formasPagamento: FormaPagamento[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private snackBar: MatSnackBar,
    private router: Router,
    private vendaService: VendaService,
    private clienteService: ClienteService,
    private formaPagamentoService: FormaPagamentoService
  ) { }

  ngOnInit(): void {
    this.vendaForm = this.fb.group({
      vendaCodigo: [this.generateVendaCodigo(), Validators.required],
      vendaData: [new Date(), Validators.required],
      cliId: [null, Validators.required],
      fpgId: [null, Validators.required],
      compras: this.fb.array([], Validators.required)
    });

    this.loadProdutos();
    this.loadClientes();
    this.loadFormasPagamento();
    this.addCompra(); // começa com um produto por padrão
  }

  // Gerar código aleatório para a venda
  generateVendaCodigo(): string {
    const codigo = Math.floor(Math.random() * 1000000);
    return codigo.toString().padStart(6, '0');
  }

  // Carrega produtos do backend
  loadProdutos(): void {
    this.productService.read().subscribe({
      next: produtos => this.products = produtos,
      error: () => this.snackBar.open('Erro ao carregar produtos', 'X', { duration: 3000 })
    });
  }

  // Carrega clientes
  loadClientes(): void {
    this.clienteService.readClientes().subscribe({
      next: clientes => this.clientes = clientes,
      error: () => this.snackBar.open('Erro ao carregar clientes', 'X', { duration: 3000 })
    });
  }

  loadFormasPagamento(): void {
    this.formaPagamentoService.read().subscribe({
      next: (formas: FormaPagamento[]) => this.formasPagamento = formas,
      error: () => this.snackBar.open('Erro ao carregar formas de pagamento', 'X', { duration: 3000 })
    });
  }

  // Getter para array de compras
  get compras(): FormArray {
    return this.vendaForm.get('compras') as FormArray;
  }

  // Cria uma nova linha de compra
  createCompra(): FormGroup {
    return this.fb.group({
      proId: [null, Validators.required],
      compraQuantidade: [1, [Validators.required, Validators.min(1)]],
      compraPrecoVenda: [0, [Validators.required, Validators.min(0)]]
    });
  }

  // Adiciona nova compra
  addCompra(): void {
    this.compras.push(this.createCompra());
  }

  // Remove uma linha de compra
  removeCompra(index: number): void {
    if (this.compras.length > 1) {
      this.compras.removeAt(index);
    }
  }

  // Atualiza preço de venda baseado no produto selecionado
  onProdutoChange(index: number): void {
    const compraGroup = this.compras.at(index);
    const proId = compraGroup.get('proId')?.value;

    const produto = this.products.find(p => p.proId === proId);
    if (produto) {
      compraGroup.patchValue({
        compraPrecoVenda: produto.proPrecoVenda
      });
    } else {
      compraGroup.patchValue({
        compraPrecoVenda: 0
      });
    }
  }

  // Cancela e volta para listagem
  cancel(): void {
    this.router.navigate(['/vendas']);
  }

  // Submete o formulário
  onSubmit(): void {
    if (this.vendaForm.invalid) {
      this.snackBar.open('Preencha todos os campos corretamente!', 'X', { duration: 3000 });
      return;
    }

    const raw = this.vendaForm.value;

    const vendaValorTotal = raw.compras.reduce(
      (sum: number, item: Compra) => sum + item.compraQuantidade * item.compraPrecoVenda,
      0
    );

    const venda: Venda = {
      ...raw,
      vendaValorTotal,
      vendaData: new Date(raw.vendaData).toISOString() // formato ISO para backend
    };

    this.vendaService.create(venda).subscribe({
      next: () => {
        this.snackBar.open('Venda criada com sucesso!', 'X', { duration: 3000 });
        this.router.navigate(['/vendas']);
      },
      error: (err) => {
        this.snackBar.open('Erro ao criar venda', 'X', { duration: 3000 });
        console.error(err);
        console.log(venda);
      }
    });
  }
}