// Importações necessárias para o componente
import { Component, OnInit } from '@angular/core';
import { Cliente } from '../cliente.model';
import { Router } from '@angular/router';
import { ClienteService } from '../cliente.service';
import { HttpClient } from '@angular/common/http'; // Para buscar CEP via API

@Component({
  selector: 'app-cliente-create',
  templateUrl: './cliente-create.component.html',
  styleUrls: ['./cliente-create.component.css']
})
export class ClienteCreateComponent implements OnInit {
  cliente: Cliente = {
    cliNome: '',
    cliCpf: '',
    conCelular: '',
    conTelefoneComercial: '',
    conEmail: '',
    endRua: '',
    endNumero: '',
    endCidade: '',
    endCep: '',
    endEstado: ''
  };

  submitted = false;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {}

  // Busca o endereço no ViaCEP sempre que o campo CEP for alterado
  buscarEnderecoPorCep(): void {
    const cep = this.cliente.endCep?.replace(/\D/g, ''); // Remove caracteres não numéricos

    if (cep && cep.length === 8) {
      this.http.get<any>(`https://viacep.com.br/ws/${cep}/json/`).subscribe({
        next: (data) => {
          if (!data.erro) {
            this.cliente.endRua = data.logradouro;
            this.cliente.endCidade = data.localidade;
            this.cliente.endEstado = data.uf;
          }
        },
        error: () => {
          console.error('Erro ao buscar o CEP');
        }
      });
    }
  }

  createCliente(): void {
    this.submitted = true;
    if (
      this.cliente.cliNome &&
      this.cliente.cliCpf &&
      this.cliente.conCelular &&
      this.cliente.conTelefoneComercial &&
      this.cliente.conEmail &&
      this.cliente.endRua &&
      this.cliente.endNumero &&
      this.cliente.endCidade &&
      this.cliente.endCep &&
      this.cliente.endEstado
    ) {
      this.clienteService.createCliente(this.cliente).subscribe(() => {
        this.clienteService.showMessage('Cliente criado!');
        this.router.navigate(['/clientes']);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/clientes']);
  }
}