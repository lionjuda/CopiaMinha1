export interface Fornecedor {
    forId?: number; // ID do fornecedor 
    forNomeFantasia: string; // Nome fantasia do fornecedor
    forCnpj: string; // CNPJ do fornecedor
    forRazaoSocial: string; // Razão social do fornecedor
    // Campos de contato
    conCelular: string;
    conTelefoneComercial: string;
    conEmail: string;
    // Campos de endereço
    endRua: string;
    endNumero: string;
    endCidade: string;
    endCep: string;
    endEstado: string;
}