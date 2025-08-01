import { Component } from '@angular/core';
import { FormaPagamento } from '../formaPagamento.model';
import { FormaPagamentoService } from '../formaPagamento.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-forma-pagamento-update',
  templateUrl: './forma-pagamento-update.component.html',
  styleUrls: ['./forma-pagamento-update.component.css']
})
export class FormaPagamentoUpdateComponent {
  formaPagamento!: FormaPagamento;

  constructor(private formaPagamentoService: FormaPagamentoService,
    private router: Router,
    private route: ActivatedRoute) {}

  ngOnInit(): void {
    const fpgId = this.route.snapshot.paramMap.get('fpgId')
    this.formaPagamentoService.readById(fpgId!).subscribe((formaPagamento: FormaPagamento) =>{
      this.formaPagamento = formaPagamento
    })
  }

  updateFormaPagamento(): void {
    this.formaPagamentoService.update(this.formaPagamento).subscribe(() => {
      this.formaPagamentoService.showMessage('Forma de pagamento excluida atualizado com sucesso!')
      this.router.navigate(['/formaPagamento'])
    })
  }

  cancel(): void {
    this.router.navigate(['/formaPagamento'])
  }
}
