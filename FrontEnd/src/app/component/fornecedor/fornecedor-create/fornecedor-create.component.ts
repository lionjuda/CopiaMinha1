import { Component, OnInit } from '@angular/core';
import { Fornecedor } from '../fornecedor.model';
import { Router } from '@angular/router';
import { FornecedorService } from '../fornecedor.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-fornecedor-create',
  templateUrl: './fornecedor-create.component.html',
  styleUrls: ['./fornecedor-create.component.css']
})
export class FornecedorCreateComponent implements OnInit {

  fornecedor: Fornecedor = {
    forNomeFantasia: '',
    forCnpj: '',
    forRazaoSocial: '',
    conCelular: '',
    conTelefoneComercial: '',
    conEmail: '',
    endRua: '',
    endNumero: '',
    endCidade: '',
    endCep: '',
    endEstado: ''
  };

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router,
    private http: HttpClient // Adicionamos o HttpClient
  ) {}

  ngOnInit(): void {}

  createFornecedor(): void {
    this.fornecedorService.createFornecedor(this.fornecedor).subscribe(() => {
      this.fornecedorService.showMessage('Fornecedor criado!');
      this.router.navigate(['/fornecedor']);
    });
  }

  cancel(): void {
    this.router.navigate(['/fornecedor']);
  }

  // Busca os dados do endereço no ViaCEP
  buscarCEP(): void {
    const cep = this.fornecedor.endCep.replace(/\D/g, ''); // Remove caracteres não numéricos
    if (cep.length === 8) {
      this.http.get<any>(`https://viacep.com.br/ws/${cep}/json/`).subscribe({
        next: (dados) => {
          if (!dados.erro) {
            this.fornecedor.endRua = dados.logradouro;
            this.fornecedor.endCidade = dados.localidade;
            this.fornecedor.endEstado = dados.uf;
          } else {
            this.fornecedorService.showMessage('CEP não encontrado.');
          }
        },
        error: () => {
          this.fornecedorService.showMessage('Erro ao buscar CEP.');
        }
      });
    }
  }

  // Contador Contato (mantive do seu código original)
  private _contatoCount = 0;

  setContatoCount(count: number) {
    this._contatoCount = count;
  }

  getContatoCount(): number {
    return this._contatoCount;
  }
}