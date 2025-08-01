export interface Product {
    proId?: number; // ID do produto (opcional)
    proNome: string; // Nome do produto
    proDescricao: string;
    proPrecoCusto: number | null; // Preço de custo do produto
    proPrecoVenda: number | null; // Preço de venda do produto
    proQuantidadeEstoque: number | null;
    proCategoria: string;
    proCodigoBarras: string;
    proMarca: string;
    proUnidadeMedida: string;
    proAtivo: string;
    proDataCadastro: string;
    proDataAtualizacao: string;
    forId: number;
}