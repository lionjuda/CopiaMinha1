import { Component } from '@angular/core';
import { Contato } from '../contato-read.model';
import { contatoService } from '../contato.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contato-delete',
  templateUrl: './contato-delete.component.html',
  styleUrls: ['./contato-delete.component.css']
})
export class ContatoDeleteComponent {
  contato!: Contato;

  constructor(
    private contatoService: contatoService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const conId = this.route.snapshot.paramMap.get('conId');
    this.contatoService.readById(conId!).subscribe(contato =>{
      this.contato = contato
    })
  }

  deleteContato(): void {
    this.contatoService.delete(this.contato.conId!).subscribe(() =>{
    this.contatoService.showMessage('Produto excluido com sucesso!')  
    this.router.navigate(['/contato'])
    })
  }

  cancel(): void{
    this.router.navigate(['/contato'])
  }
}
