import { Component, OnInit } from '@angular/core';
import { FormaPagamento } from '../formaPagamento.model';
import { FormaPagamentoService } from '../formaPagamento.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forma-pagamento-create',
  templateUrl: './forma-pagamento-create.component.html',
  styleUrls: ['./forma-pagamento-create.component.css']
})
export class FormaPagamentoCreateComponent implements OnInit {

  formaPagamento: FormaPagamento = {
    fpgTipo: '',
    fpgDescricao: '',
    fpgPermiteParcelamento: false,
    fpgNumMaxParcelas: 1,
    fpgTaxaAdicional: 0
  };

  constructor(
    private formaPagamentoService: FormaPagamentoService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  createFormaPagamento(): void {
    this.formaPagamentoService.create(this.formaPagamento).subscribe(() => {
      this.formaPagamentoService.showMessage('Forma de pagamento criada com sucesso!');
      this.router.navigate(['/formaPagamento']);
    });
  }

  cancel(): void {
    this.router.navigate(['/formaPagamento']);
  }
}