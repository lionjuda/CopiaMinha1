import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contato } from 'src/app/component/contato/contato-read.model';
import { contatoService } from 'src/app/component/contato/contato.service';

@Component({
  selector: 'app-contato-crud',
  templateUrl: './contato-crud.component.html',
  styleUrls: ['./contato-crud.component.css']
})
export class ContatoCrudComponent implements OnInit {
  searchTerm: string = '';
  allContatos: Contato[] = [];
  filteredContatos: Contato[] = [];

  constructor(
    private router: Router,
    private contatoService: contatoService
  ) {}

  ngOnInit(): void {
    this.contatoService.read().subscribe(contatos => {
      this.allContatos = contatos;
      this.filteredContatos = contatos;
    });
  }

  navigateToContatoCreate(): void {
    this.router.navigate(['/contato/create']);
  }

  filterContatos(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredContatos = this.allContatos.filter(c =>
      (c.conTelefoneComercial?.toLowerCase().includes(term) ?? false) ||
      (c.conCelular?.toLowerCase().includes(term) ?? false) ||
      (c.conEmail?.toLowerCase().includes(term) ?? false)
    );
  }
}