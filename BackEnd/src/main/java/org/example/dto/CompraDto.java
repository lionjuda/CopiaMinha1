package org.example.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CompraDto {

    @NotNull
    private Long proId;

    @NotNull
    @Min(1)
    private Integer compraQuantidade;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal compraPrecoVenda;

    public CompraDto() {
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
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
}