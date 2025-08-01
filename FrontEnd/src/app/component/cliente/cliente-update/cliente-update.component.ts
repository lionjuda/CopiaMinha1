import { Component } from '@angular/core';
import { Cliente } from '../cliente.model';
import { ClienteService } from '../cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http'; // para buscar na API ViaCEP

@Component({
  selector: 'app-cliente-update',
  templateUrl: './cliente-update.component.html',
  styleUrls: ['./cliente-update.component.css']
})
export class ClienteUpdateComponent {
  cliente!: Cliente;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

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

  // Busca endereço pelo CEP
  buscarEndereco(): void {
    const cep = this.cliente.endCep?.replace(/\D/g, '');
    if (cep && cep.length === 8) {
      this.http.get(`https://viacep.com.br/ws/${cep}/json/`).subscribe((dados: any) => {
        if (!dados.erro) {
          this.cliente.endRua = dados.logradouro;
          this.cliente.endCidade = dados.localidade;
          this.cliente.endEstado = dados.uf;
        }
      });
    }
  }

  updateCliente(): void {
    this.clienteService.updateCliente(this.cliente).subscribe(() => {
      this.clienteService.showMessage('Cliente atualizado com sucesso!');
      this.router.navigate(['/clientes']);
    });
  }

  cancel(): void {
    this.router.navigate(['/clientes']);
  }
}