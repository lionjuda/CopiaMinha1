package org.example.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VENDA_ID")
    private Long vendaId;

    @ManyToOne
    @JoinColumn(name = "VENDA_CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<Compra> compras;

    @NotNull(message = "O valor total da venda é obrigatório.")
    @Digits(integer = 10, fraction = 2, message = "O valor total deve ter no máximo 10 dígitos inteiros e 2 casas decimais.")
    @Column(name = "VENDA_VALOR_TOTAL", nullable = false, precision = 12, scale = 2)
    private BigDecimal vendaValorTotal;

    @ManyToOne
    @JoinColumn(name = "VENDA_FORMA_PAGAMENTO_ID", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "VENDA_CODIGO", nullable = false, length = 6)
    @Size(min = 6, max = 6, message = "O código da venda deve ter exatamente 6 caracteres")
    private String vendaCodigo;

    @Column(name = "VENDA_DATA", nullable = false)
    private LocalDateTime vendaData;

    public Venda() {
    }

    public Venda(Long vendaId, Cliente cliente, List<Compra> compras, BigDecimal vendaValorTotal, FormaPagamento formaPagamento, String vendaCodigo, LocalDateTime vendaData) {
        this.vendaId = vendaId;
        this.cliente = cliente;
        this.compras = compras;
        this.vendaValorTotal = vendaValorTotal;
        this.formaPagamento = formaPagamento;
        this.vendaCodigo = vendaCodigo;
        this.vendaData = vendaData;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public BigDecimal getVendaValorTotal() {
        return vendaValorTotal;
    }

    public void setVendaValorTotal(BigDecimal vendaValorTotal) {
        this.vendaValorTotal = vendaValorTotal;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
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
}