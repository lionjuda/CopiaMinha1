package org.example.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaDto {

    private Long vendaId;

    @NotBlank
    private String vendaCodigo;

    private LocalDateTime vendaData;

    @NotNull
    private Long cliId;

    @NotNull
    private Long fpgId;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal vendaValorTotal;

    @NotEmpty
    private List<CompraDto> compras;

    public VendaDto() {
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public String getVendaCodigo() {
        return vendaCodigo;
    }

    public void setVendaCodigo(String vendaCodigo) {
        this.vendaCodigo = vendaCodigo;
    }

    public LocalDateTime getVendaData() {
        return vendaData;
    }

    public void setVendaData(LocalDateTime vendaData) {
        this.vendaData = vendaData;
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public Long getFpgId() {
        return fpgId;
    }

    public void setFpgId(Long fpgId) {
        this.fpgId = fpgId;
    }

    public BigDecimal getVendaValorTotal() {
        return vendaValorTotal;
    }

    public void setVendaValorTotal(BigDecimal vendaValorTotal) {
        this.vendaValorTotal = vendaValorTotal;
    }

    public List<CompraDto> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraDto> compras) {
        this.compras = compras;
    }
}