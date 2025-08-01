import { Component, Input } from '@angular/core';
import { Fornecedor } from '../fornecedor.model';
import { FornecedorService } from '../fornecedor.service';

@Component({
  selector: 'app-fornecedor-read',
  templateUrl: './fornecedor-read.component.html',
  styleUrls: ['./fornecedor-read.component.css']
})
export class FornecedorReadComponent {
  @Input() fornecedores: Fornecedor[] = []; // Recebe a lista de fornecedores do componente pai
  
  displayedColumns = ['forId', 'forNomeFantasia', 'forCnpj', 'forRazaoSocial', 'action'];

  // Injeta o serviço FornecedorService no construtor
  constructor(private fornecedorService: FornecedorService) { }

  // Método executado ao inicializar o componente
  ngOnInit(): void {
    this.fornecedorService.readFornecedor().subscribe(fornecedor => {
      this.fornecedores = fornecedor; // Atribui os dados recebidos à lista de fornecedores
      console.log(fornecedor); // Exibe os dados no console para depuração
    });
  }
}