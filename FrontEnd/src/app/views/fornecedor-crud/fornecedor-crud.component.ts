import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Fornecedor } from 'src/app/component/fornecedor/fornecedor.model';
import { FornecedorService } from 'src/app/component/fornecedor/fornecedor.service';

@Component({
  selector: 'app-fornecedor-crud',
  templateUrl: './fornecedor-crud.component.html',
  styleUrls: ['./fornecedor-crud.component.css']
})
export class FornecedorCrudComponent implements OnInit {
  searchTerm: string = '';
  allFornecedores: Fornecedor[] = [];
  filteredFornecedores: Fornecedor[] = [];

  constructor(
    private router: Router,
    private fornecedorService: FornecedorService
  ) {}

  ngOnInit(): void {
    this.fornecedorService.readFornecedor().subscribe(fornecedores => {
      this.allFornecedores = fornecedores;
      this.filteredFornecedores = fornecedores;
    });
  }

  navigateToFornecedorCreate(): void {
    this.router.navigate(['/fornecedor/create']);
  }

  filterFornecedores(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredFornecedores = this.allFornecedores.filter(f =>
      f.forNomeFantasia.toLowerCase().includes(term) ||
      f.forCnpj.toLowerCase().includes(term) ||
      f.forRazaoSocial.toLowerCase().includes(term)
    );
  }
}