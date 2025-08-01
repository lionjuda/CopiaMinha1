import { Component, Input } from '@angular/core';
import { Cliente } from '../cliente.model';
import { ClienteService } from '../cliente.service';

@Component({
  selector: 'app-cliente-read',
  templateUrl: './cliente-read.component.html',
  styleUrls: ['./cliente-read.component.css']
})
export class ClienteReadComponent {
  // Recebe os clientes do componente pai (ClienteCrudComponent)
  @Input() clientes: Cliente[] = [];

  // Define as colunas a serem exibidas
  displayedColumns = ['cliId', 'cliNome', 'cliCpf', 'conEmail', 'conCelular', 'conTelefoneComercial', 'action'];

  // Construtor que injeta o serviço ClienteService
  constructor(private clienteService: ClienteService) { }

  // Método chamado ao inicializar o componente
  ngOnInit(): void {
    // Chama o método 'read' do serviço para buscar os dados dos clientes
    this.clienteService.readClientes().subscribe(clientes => {
      this.clientes = clientes; // Armazena os dados retornados no array 'cliente'
      console.log(clientes); // Exibe os dados no console para depuração
    });
  }
}