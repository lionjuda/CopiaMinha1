package org.example.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compraId;

    @ManyToOne
    @JoinColumn(name = "proId", nullable = false)
    private Produto produto;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    @Column(name = "COMPRA_QUANTIDADE", nullable = false)
    private Integer compraQuantidade;

    @NotNull
    @Digits(integer = 10, fraction = 2, message = "O preço de venda ter no máximo 10 dígitos inteiros e 2 casas decimais.")
    @Column(name = "COMPRA_PRECO_VENDA", nullable = false, precision = 12, scale = 2)
    private BigDecimal compraPrecoVenda;

    @ManyToOne
    @JoinColumn(name = "vendaId", nullable = false)
    private Venda venda;

    public Compra() {
    }

    public Compra(Long compraId, Produto produto, Integer compraQuantidade, BigDecimal compraPrecoVenda, Venda venda) {
        this.compraId = compraId;
        this.produto = produto;
        this.compraQuantidade = compraQuantidade;
        this.compraPrecoVenda = compraPrecoVenda;
        this.venda = venda;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getCompraQuantidade() {
        return compraQuantidade;
    }

    public void setCompraQuantidade(Integer compraQuantidade) {
        this.compraQuantidade = compraQuantidade;
    }

    public BigDecimal getCompraPrecoVenda() {
        return compraPrecoVenda;
    }

    public void setCompraPrecoVenda(BigDecimal compraPrecoVenda) {
        this.compraPrecoVenda = compraPrecoVenda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}