import { Component } from '@angular/core';
import { Fornecedor } from '../fornecedor.model';
import { FornecedorService } from '../fornecedor.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-fornecedor-update',
  templateUrl: './fornecedor-update.component.html',
  styleUrls: ['./fornecedor-update.component.css']
})
export class FornecedorUpdateComponent {
  fornecedor!: Fornecedor;

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const forId = this.route.snapshot.paramMap.get('forId');
    this.fornecedorService.readFornecedorById(forId!).subscribe((fornecedor: Fornecedor) => {
      this.fornecedor = fornecedor;
    });
  }

  updateFornecedor(): void {
    this.fornecedorService.updateFornecedor(this.fornecedor).subscribe(() => {
      this.fornecedorService.showMessage('Fornecedor atualizado com sucesso!');
      this.router.navigate(['/fornecedor']);
    });
  }

  cancel(): void {
    this.router.navigate(['/fornecedor']);
  }

  buscarCEP(): void {
    const cep = this.fornecedor.endCep?.replace(/\D/g, ''); // Remove caracteres não numéricos

    if (!cep || cep.length !== 8) {
      this.fornecedorService.showMessage('CEP inválido!');
      return;
    }

    fetch(`https://viacep.com.br/ws/${cep}/json/`)
      .then(res => res.json())
      .then(data => {
        if (!data.erro) {
          this.fornecedor.endRua = data.logradouro || '';
          this.fornecedor.endCidade = data.localidade || '';
          this.fornecedor.endEstado = data.uf || '';
        } else {
          this.fornecedorService.showMessage('CEP não encontrado!');
        }
      })
      .catch(() => {
        this.fornecedorService.showMessage('Erro ao buscar o CEP!');
      });
  }
}