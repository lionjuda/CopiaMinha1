import { Component, Input } from '@angular/core';
import { Contato } from '../contato-read.model';

@Component({
  selector: 'app-contato-read',
  templateUrl: './contato-read.component.html',
  styleUrls: ['./contato-read.component.css']
})
export class ContatoReadComponent {
  // Recebe a lista de contatos do componente pai
  @Input() contatos: Contato[] = [];

  // Define as colunas exibidas na tabela
  displayedColumns = ['conId', 'conTelefoneComercial', 'conCelular', 'conEmail', 'action'];
}