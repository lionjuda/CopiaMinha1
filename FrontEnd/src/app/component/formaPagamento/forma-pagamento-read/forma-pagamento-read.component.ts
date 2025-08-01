import { Component, Input } from '@angular/core';
import { FormaPagamento } from '../formaPagamento.model';

@Component({
  selector: 'app-forma-pagamento-read',
  templateUrl: './forma-pagamento-read.component.html',
  styleUrls: ['./forma-pagamento-read.component.css']
})
export class FormaPagamentoReadComponent {
  // Recebe a lista de formas de pagamento via input do componente pai
  @Input() formasPagamento: FormaPagamento[] = [];

  // Colunas da tabela
  displayedColumns = [
  'fpgId',
  'fpgTipo',
  'fpgPermiteParcelamento',
  'fpgNumMaxParcelas',
  'fpgTaxaAdicional',
  'action'
  ];
}