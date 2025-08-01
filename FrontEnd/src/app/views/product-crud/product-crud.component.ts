import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/component/product/product.model';
import { ProductService } from 'src/app/component/product/product.service';

@Component({
  selector: 'app-product-crud',
  templateUrl: './product-crud.component.html',
  styleUrls: ['./product-crud.component.css']
})
export class ProductCrudComponent implements OnInit {
  searchTerm: string = '';
  allProducts: Product[] = [];
  filteredProducts: Product[] = [];

  constructor(
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.productService.read().subscribe(products => {
      this.allProducts = products;
      this.filteredProducts = products;
    });
  }

  navigateToProductCreate(): void {
    this.router.navigate(['/products/create']);
  }

  filterProducts(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredProducts = this.allProducts.filter(p =>
      p.proNome.toLowerCase().includes(term)
    );
  }
}