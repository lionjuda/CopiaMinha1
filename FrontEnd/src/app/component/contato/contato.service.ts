import { HttpClient } from "@angular/common/http"; // Importa o cliente HTTP para realizar requisições
import { Injectable } from "@angular/core"; // Permite que a classe seja injetada como serviço
import { MatSnackBar } from "@angular/material/snack-bar"; // Importa o componente para exibir mensagens
import { Observable } from "rxjs"; // Importa o tipo Observable para lidar com operações assíncronas
import { Contato } from "./contato-read.model"; // Importa o modelo de dados Contato

@Injectable({
    providedIn: 'root' // Define que o serviço será fornecido na raiz da aplicação
})
export class contatoService {

    baseUrl = "http://localhost:8080/contatos"; // URL base para as requisições HTTP

    constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

    // Exibe uma mensagem de notificação usando o MatSnackBar
    showMessage(msg: string): void {
        this.snackBar.open(msg, 'X', {
            duration: 3000, // Duração da mensagem em milissegundos
            horizontalPosition: "right", // Posição horizontal
            verticalPosition: "top" // Posição vertical
        });
    }

    // Cria um novo contato enviando uma requisição POST
    create(contato: Contato): Observable<Contato> {
        return this.http.post<Contato>(this.baseUrl, contato);
    }

    // Obtém a lista de contatos enviando uma requisição GET
    read(): Observable<Contato[]> {
        return this.http.get<Contato[]>(this.baseUrl);
    }

    // Obtém um contato específico pelo ID enviando uma requisição GET
    readById(id: string): Observable<Contato> {
        const url = `${this.baseUrl}/${id}`; // Concatena o ID à URL base
        return this.http.get<Contato>(url);
    }

    // Atualiza um contato existente enviando uma requisição PUT
    update(contato: Contato): Observable<Contato> {
        const url = `${this.baseUrl}/${contato.conId}`; // Concatena o ID do contato à URL base
        return this.http.put<Contato>(url, contato);
    }

    // Exclui um contato pelo ID enviando uma requisição DELETE
    delete(id: number): Observable<Contato> {
        const url = `${this.baseUrl}/${id}`; // Concatena o ID à URL base
        return this.http.delete<Contato>(url);
    }

    //Contador Contato
    private _contatoCount = 0;

    setContatoCount(count: number) {
    this._contatoCount = count;
  }

    getContatoCount(): number {
    return this._contatoCount;
  }
}