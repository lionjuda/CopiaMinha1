export interface Compra {
    proId?: number;
    compraQuantidade: number;
    compraPrecoVenda: number;
}

export interface Venda {
    vendaId?: number;
    vendaCodigo: string;
    vendaData: string;
    cliId: number;
    fpgId: number;
    vendaValorTotal: number;
    compras: Compra[];
}