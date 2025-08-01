import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Venda } from './venda.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendaService {

  baseUrl = 'http://localhost:8080/vendas';

  constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

  showMessage(msg: string): void {
    this.snackBar.open(msg, 'X', {
      duration: 3000,
      horizontalPosition: 'right',
      verticalPosition: 'top'
    });
  }

  create(venda: Venda): Observable<Venda> {
    return this.http.post<Venda>(this.baseUrl, venda);
  }

  read(): Observable<Venda[]>{
    return this.http.get<Venda[]>(this.baseUrl);
  }

  readById(id: string): Observable<Venda>{
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Venda>(url);
  }

  //Contador Vendas
  private _vendaCount = 0;

  setVendaCount(count: number) {
  this._vendaCount = count;
  }

  getVendaCount(): number {
  return this._vendaCount;
  }
}