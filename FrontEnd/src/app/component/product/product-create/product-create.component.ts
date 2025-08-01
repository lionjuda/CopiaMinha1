import { Component, OnInit } from '@angular/core';
import { Product } from '../product.model';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';
import { FornecedorService } from '../../fornecedor/fornecedor.service';
import { Fornecedor } from '../../fornecedor/fornecedor.model';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  product: Product = {
    proNome: '',
    proDescricao: '',
    proPrecoCusto: 0,
    proPrecoVenda: 0,
    proQuantidadeEstoque: 0,
    proCategoria: '',
    proCodigoBarras: '',
    proMarca: '',
    proUnidadeMedida: '',
    proAtivo: 'Ativo',
    proDataCadastro: new Date().toISOString(),
    proDataAtualizacao: new Date().toISOString(),
    forId: 0
  };

  fornecedores: Fornecedor[] = []; // <--- AQUI: declarar a propriedade

  constructor(
    private productService: ProductService,
    private fornecedorService: FornecedorService, // <--- AQUI: injetar o serviço
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fornecedorService.readFornecedor().subscribe((fornecedores: Fornecedor[]) => {
      this.fornecedores = fornecedores;
    });
  }

  createProduct(): void {
    this.productService.create(this.product).subscribe(() => {
      this.productService.showMessage('Produto criado!');
      this.router.navigate(['/products']);
    });
  }

  cancel(): void {
    this.router.navigate(['/products']);
  }
}