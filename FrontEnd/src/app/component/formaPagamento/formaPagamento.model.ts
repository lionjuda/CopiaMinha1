// Define a interface FormaPagamento que representa o modelo de dados
export interface FormaPagamento {
    fpgId?: number;
    fpgTipo: string;
    fpgDescricao: string;
    fpgPermiteParcelamento: boolean;
    fpgNumMaxParcelas: number;
    fpgTaxaAdicional: number;
}