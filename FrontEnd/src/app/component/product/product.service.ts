import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root' // Define o serviço como singleton no root module
})
export class ProductService {

  baseUrl = "http://localhost:8080/produtos"; // URL base da API

  constructor(private snackBar: MatSnackBar, private http: HttpClient) {}

  // Exibe uma mensagem de notificação
  showMessage(msg: string): void {
    this.snackBar.open(msg, 'X', {
      duration: 3000, // Duração da mensagem em milissegundos
      horizontalPosition: "right", // Posição horizontal
      verticalPosition: "top" // Posição vertical
    });
  }

  // Cria um novo produto
  create(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }

  // Obtém a lista de produtos
  read(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl);
  }

  // Obtém um produto pelo ID
  readById(id: string): Observable<Product> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Product>(url);
  }

  // Atualiza um produto existente
  update(product: Product): Observable<Product> {
    const url = `${this.baseUrl}/${product.proId}`;
    return this.http.put<Product>(url, product);
  }

  // Exclui um produto pelo ID
  delete(id: number): Observable<Product> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<Product>(url);
  }

  //Contador Produto
  private _productCount = 0;

  setProductCount(count: number) {
  this._productCount = count;
  }

  getProductCount(): number {
  return this._productCount;
  }
}