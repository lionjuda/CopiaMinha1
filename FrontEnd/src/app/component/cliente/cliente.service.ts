// Importações essenciais para o funcionamento do serviço
import { HttpClient } from "@angular/common/http"; 
import { Injectable } from "@angular/core"; 
import { MatSnackBar } from "@angular/material/snack-bar";
import { Observable } from "rxjs"; 
import { Cliente } from './cliente.model';
import { Endereco } from "../endereco/endereco.model"; 
import { Contato } from "../contato/contato-read.model";

// Decorador que define o serviço como disponível em toda a aplicação
@Injectable({
    providedIn: 'root' // O serviço será injetado na raiz do projeto
})
export class ClienteService {

        readById(id: string): Observable<Cliente> {
    const url = `${this.clienteBaseUrl}/${id}`;
    return this.http.get<Cliente>(url);
}

    // URL base para as requisições HTTP relacionadas ao cliente
    clienteBaseUrl = "http://localhost:8080/clientes";
    private contatoBaseUrl = "http://localhost:9090/contatos";
    private enderecoBaseUrl = "http://localhost:9090/enderecos";

    // Construtor que injeta dependências necessárias
    constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

    // Método para exibir mensagens de notificação
    showMessage(msg: string): void {
        this.snackBar.open(msg, 'X', {
            duration: 3000, // Duração da mensagem em milissegundos
            horizontalPosition: "right", // Posição horizontal
            verticalPosition: "top" // Posição vertical
        });
    }

    //Cliente
    // Método para criar um novo cliente
    createCliente(cliente: Cliente): Observable<Cliente> {
        return this.http.post<Cliente>(this.clienteBaseUrl, cliente); 
    }

    // Método para obter a lista de clientes
    readClientes(): Observable<Cliente[]> {
        return this.http.get<Cliente[]>(this.clienteBaseUrl); 
    }

    // Método para obter um cliente pelo ID
    readClienteById(id: string): Observable<Cliente> {
        const url = `${this.clienteBaseUrl}/${id}`; // Concatena o ID na URL
        return this.http.get<Cliente>(url);
    }

    // Método para atualizar os dados de um cliente
    updateCliente(cliente: Cliente): Observable<Cliente> {
        const url = `${this.clienteBaseUrl}/${cliente.cliId}`; // Concatena o ID do cliente na URL
        return this.http.put<Cliente>(url, cliente); // Envia uma requisição PUT
    }

    // Método para deletar um cliente pelo ID
    deleteCliente(id: number): Observable<Cliente> {
        const url = `${this.clienteBaseUrl}/${id}`; // Concatena o ID na URL
        return this.http.delete<Cliente>(url); // Envia uma requisição DELETE
    }

    //Contato
    createContato(contato: Contato): Observable<Contato> {
        return this.http.post<Contato>(this.contatoBaseUrl, contato);
    }

    readContatos(): Observable<Contato[]> {
        return this.http.get<Contato[]>(this.contatoBaseUrl);
    }

    readContatoById(id: string): Observable<Contato> {
        const url = `${this.contatoBaseUrl}/${id}`;
        return this.http.get<Contato>(url);
    }

    updateContato(contato: Contato): Observable<Contato> {
        const url = `${this.contatoBaseUrl}/${contato.conId}`;
        return this.http.put<Contato>(url, contato);
    }

    deleteContato(id: number): Observable<Contato> {
        const url = `${this.contatoBaseUrl}/${id}`;
        return this.http.delete<Contato>(url);
    }

    //Endereco
    createEndereco(endereco: Endereco): Observable<Endereco> {
        return this.http.post<Endereco>(this.enderecoBaseUrl, endereco);
    }

    readEnderecos(): Observable<Endereco[]> {
        return this.http.get<Endereco[]>(this.enderecoBaseUrl);
    }

    readEnderecoById(id: string): Observable<Endereco> {
        const url = `${this.enderecoBaseUrl}/${id}`;
        return this.http.get<Endereco>(url);
    }

    updateEndereco(endereco: Endereco): Observable<Endereco> {
        const url = `${this.enderecoBaseUrl}/${endereco.endId}`;
        return this.http.put<Endereco>(url, endereco);
    }

    deleteEndereco(id: number): Observable<Endereco> {
        const url = `${this.enderecoBaseUrl}/${id}`;
        return this.http.delete<Endereco>(url);
    }

    //Contador Cliente
  private _clienteCount = 0;

  setClienteCount(count: number) {
  this._clienteCount = count;
  }

  getClienteCount(): number {
  return this._clienteCount;
  }
}