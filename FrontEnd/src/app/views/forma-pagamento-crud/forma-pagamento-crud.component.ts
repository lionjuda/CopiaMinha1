import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormaPagamento } from 'src/app/component/formaPagamento/formaPagamento.model';
import { FormaPagamentoService } from 'src/app/component/formaPagamento/formaPagamento.service';

@Component({
  selector: 'app-forma-pagamento-crud',
  templateUrl: './forma-pagamento-crud.component.html',
  styleUrls: ['./forma-pagamento-crud.component.css']
})
export class FormaPagamentoCrudComponent implements OnInit {
  searchTerm: string = '';
  allFormasPagamento: FormaPagamento[] = [];
  filteredFormasPagamento: FormaPagamento[] = [];

  constructor(
    private router: Router,
    private formaPagamentoService: FormaPagamentoService
  ) {}

  ngOnInit(): void {
    this.formaPagamentoService.read().subscribe(formas => {
      this.allFormasPagamento = formas;
      this.filteredFormasPagamento = formas;
    });
  }

  navigateToFormaPagamentoCreate(): void {
    this.router.navigate(['/formaPagamento/create']);
  }

  filterFormasPagamento(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredFormasPagamento = this.allFormasPagamento.filter(f =>
      f.fpgDescricao.toLowerCase().includes(term)
    );
  }
}