// Importações necessárias para o componente
import { Component, OnInit } from '@angular/core'; // Para criar o componente e inicializá-lo
import { Contato } from '../contato-read.model'; // Modelo de dados do contato
import { Router } from '@angular/router'; // Para navegação entre rotas
import { contatoService } from '../contato.service'; // Serviço para manipulação de dados do contato

// Decorador que define o componente Angular
@Component({
  selector: 'app-contato-create', // Nome do seletor usado no HTML
  templateUrl: './contato-create.component.html', // Caminho para o template HTML do componente
  styleUrls: ['./contato-create.component.css'] // Caminho para o arquivo de estilos CSS do componente
})
export class ContatoCreateComponent implements OnInit {
  // Objeto contato inicializado com valores vazios
  contato: Contato = {
    conCelular: '', // Número de celular do contato
    conTelefoneComercial: '', // Telefone comercial do contato
    conEmail: '', // E-mail do contato
  };

  // Construtor que injeta o serviço contatoService e o Router
  constructor(private contatoService: contatoService,
              private router: Router) { }

  // Método chamado ao inicializar o componente
  ngOnInit(): void {
  }

  // Método para criar um novo contato
  createContato(): void {
    this.contatoService.create(this.contato).subscribe(() => {
      this.contatoService.showMessage('contato criado!'); // Exibe mensagem de sucesso
      this.router.navigate(['/contato']); // Navega para a lista de contatos
    });
  }

  // Método para cancelar a operação e voltar para a lista de contatos
  cancel(): void {
    this.router.navigate(['/contato']); // Navega para a lista de contatos
  }
}