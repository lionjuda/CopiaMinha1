import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './component/template/header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NavComponent } from './component/template/nav/nav.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { FooterComponent } from './component/template/footer/footer.component';
import { HomeComponent } from './views/home/home.component';
import { MatCardModule } from '@angular/material/card';
import { ProductCrudComponent } from './views/product-crud/product-crud.component';
import { ProductReadComponent } from './component/product/product-read/product-read.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FornecedorReadComponent } from './component/fornecedor/fornecedor-read/fornecedor-read.component';
import { FornecedorCrudComponent } from './views/fornecedor-crud/fornecedor-crud.component';
import { ClienteReadComponent } from './component/cliente/cliente-read/cliente-read.component';
import { ClienteCrudComponent } from './views/cliente-crud/cliente-crud.component';
import { FormaPagamentoReadComponent } from './component/formaPagamento/forma-pagamento-read/forma-pagamento-read.component'; 
import { FormaPagamentoCrudComponent } from './views/forma-pagamento-crud/forma-pagamento-crud.component';
import { ContatoReadComponent } from './component/contato/contato-read/contato-read.component';
import { ContatoCrudComponent } from './views/contato-crud/contato-crud.component';
import { ProductCreateComponent } from './component/product/product-create/product-create.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { ClienteCreateComponent } from './component/cliente/cliente-create/cliente-create.component';
import { FornecedorCreateComponent } from './component/fornecedor/fornecedor-create/fornecedor-create.component';
import { ContatoCreateComponent } from './component/contato/contato-create/contato-create.component';
import { MatTableModule } from '@angular/material/table';
import { FormaPagamentoCreateComponent } from './component/formaPagamento/forma-pagamento-create/forma-pagamento-create.component';
import { ProductUpdateComponent } from './component/product/product-update/product-update.component';
import { ProductDeleteComponent } from './component/product/product-delete/product-delete.component';
import { ClienteDeleteComponent } from './component/cliente/cliente-delete/cliente-delete.component';
import { ClienteUpdateComponent } from './component/cliente/cliente-update/cliente-update.component';
import { FornecedorUpdateComponent } from './component/fornecedor/fornecedor-update/fornecedor-update.component';
import { FornecedorDeleteComponent } from './component/fornecedor/fornecedor-delete/fornecedor-delete.component';
import { FormaPagamentoUpdateComponent } from './component/formaPagamento/forma-pagamento-update/forma-pagamento-update.component';
import { FormaPagamentoDeleteComponent } from './component/formaPagamento/forma-pagamento-delete/forma-pagamento-delete.component';
import { ContatoUpdateComponent } from './component/contato/contato-update/contato-update.component';
import { ContatoDeleteComponent } from './component/contato/contato-delete/contato-delete.component';
import { MatSelectModule } from '@angular/material/select'; // Adicionado
import { MatDatepickerModule } from '@angular/material/datepicker'; // Adicionado
import { MatNativeDateModule } from '@angular/material/core'; // Adicionado
import { VendaCreateComponent } from './component/venda/venda-create/venda-create.component';
import { VendaCrudComponent } from './views/venda-crud/venda-crud.component';
import { VendaReadComponent } from './component/venda/venda-read/venda-read.component';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavComponent,
    FooterComponent,
    HomeComponent,
    ProductCrudComponent,
    ProductReadComponent,
    FornecedorReadComponent,
    FornecedorCrudComponent,
    ClienteReadComponent,
    ClienteCrudComponent,
    FormaPagamentoReadComponent,
    FormaPagamentoCrudComponent,
    ContatoReadComponent,
    ContatoCrudComponent,
    ProductCreateComponent,
    ClienteCreateComponent,
    FornecedorCreateComponent,
    ContatoCreateComponent,
    FormaPagamentoCreateComponent,
    ProductUpdateComponent,
    ProductDeleteComponent,
    ClienteDeleteComponent,
    ClienteUpdateComponent,
    FornecedorUpdateComponent,
    FornecedorDeleteComponent,
    FormaPagamentoUpdateComponent,
    FormaPagamentoDeleteComponent,
    ContatoUpdateComponent,
    ContatoDeleteComponent,
    VendaCreateComponent,
    VendaCrudComponent,
    VendaReadComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    MatTableModule,
    MatSelectModule,               // Adicionado
    MatDatepickerModule,           // Adicionado
    MatNativeDateModule,          // Adicionado
    ReactiveFormsModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
