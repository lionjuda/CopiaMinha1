import { Component } from '@angular/core';
import { FormaPagamento } from '../formaPagamento.model';
import { FormaPagamentoService } from '../formaPagamento.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-forma-pagamento-delete',
  templateUrl: './forma-pagamento-delete.component.html',
  styleUrls: ['./forma-pagamento-delete.component.css']
})
export class FormaPagamentoDeleteComponent {
  formaPagamento!: FormaPagamento;

  constructor(
    private formaPagamentoService: FormaPagamentoService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const fpgId = this.route.snapshot.paramMap.get('fpgId');
    this.formaPagamentoService.readById(fpgId!).subscribe(formaPagamento =>{
      this.formaPagamento = formaPagamento
    })
  }

  deleteFormaPagamento(): void {
    this.formaPagamentoService.delete(this.formaPagamento.fpgId!).subscribe(() =>{
    this.formaPagamentoService.showMessage('Produto excluido com sucesso!')  
    this.router.navigate(['/formaPagamento'])
    })
  }

  cancel(): void{
    this.router.navigate(['/formaPagamento'])
  }
}
