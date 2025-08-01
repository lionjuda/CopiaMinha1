import { Component } from '@angular/core';
import { Contato } from '../contato-read.model';
import { contatoService } from '../contato.service'; // Corrigido para PascalCase
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contato-update',
  templateUrl: './contato-update.component.html',
  styleUrls: ['./contato-update.component.css']
})
export class ContatoUpdateComponent {
  contato!: Contato;

  constructor(
    private contatoService: contatoService, // Corrigido para camelCase
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const conId = this.route.snapshot.paramMap.get('conId');
    this.contatoService.readById(conId!).subscribe((contato: Contato) => {
      this.contato = contato;
    });
  }

  updateContato(): void {
    this.contatoService.update(this.contato).subscribe(() => {
      this.contatoService.showMessage('Contato atualizado com sucesso!');
      this.router.navigate(['/contato']);
    });
  }

  cancel(): void {
    this.router.navigate(['/contato']);
  }
}