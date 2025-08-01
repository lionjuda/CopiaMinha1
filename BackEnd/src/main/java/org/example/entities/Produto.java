package org.example.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_ID")
    private Long proId;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    @Column(name = "PRO_NOME", length = 100, nullable = false)
    private String proNome;

    @NotBlank(message = "A descrição do produto é obrigatória.")
    @Size(max = 255, message = "A descrição do produto deve ter no máximo 255 caracteres.")
    @Column(name = "PRO_DESCRICAO", length = 255, nullable = false)
    private String proDescricao;

    @NotNull(message = "O preço de custo é obrigatório.")
    @Digits(integer = 10, fraction = 2, message = "O preço de custo deve ter no máximo 10 dígitos inteiros e 2 casas decimais.")
    @Column(name = "PRO_PRECO_CUSTO", nullable = false, precision = 12, scale = 2)
    private BigDecimal proPrecoCusto;

    @NotNull(message = "O preço de venda é obrigatório.")
    @Digits(integer = 10, fraction = 2, message = "O preço de venda deve ter no máximo 10 dígitos inteiros e 2 casas decimais.")
    @Column(name = "PRO_PRECO_VENDA", nullable = false, precision = 12, scale = 2)
    private BigDecimal proPrecoVenda;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    @Column(name = "PRO_QUANTIDADE_ESTOQUE", nullable = false)
    private Integer proQuantidadeEstoque;

    @NotBlank(message = "A categoria do produto é obrigatória.")
    @Size(max = 50, message = "A categoria do produto deve ter no máximo 50 caracteres.")
    @Column(name = "PRO_CATEGORIA", length = 50, nullable = false)
    private String proCategoria;

    @NotBlank(message = "O código de barras do produto é obrigatório.")
    @Size(max = 50, message = "O código de barras deve ter no máximo 50 caracteres.")
    @Column(name = "PRO_CODIGO_BARRAS", length = 50, nullable = false, unique = true)
    private String proCodigoBarras;

    @NotBlank(message = "A marca do produto é obrigatória.")
    @Size(max = 50, message = "A marca do produto deve ter no máximo 50 caracteres.")
    @Column(name = "PRO_MARCA", length = 50, nullable = false)
    private String proMarca;

    @NotBlank(message = "A unidade de medida do produto é obrigatória.")
    @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres.")
    @Column(name = "PRO_UNIDADE_MEDIDA", length = 20, nullable = false)
    private String proUnidadeMedida;

    @NotBlank(message = "O status de ativo do produto é obrigatório.")
    @Pattern(regexp = "^(Ativo|Inativo)$", message = "O status do produto deve ser 'Ativo' ou 'Inativo'.")
    @Column(name = "PRO_ATIVO", length = 5, nullable = false)
    private String proAtivo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "PRO_DATA_CADASTRO", nullable = false)
    private LocalDateTime proDataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "PRO_DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime proDataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "FOR_ID", nullable = false)
    private Fornecedor fornecedor;

    public Produto() {}

    // Getters e Setters
    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }

    public String getProDescricao() {
        return proDescricao;
    }

    public void setProDescricao(String proDescricao) {
        this.proDescricao = proDescricao;
    }

    public BigDecimal getProPrecoCusto() {
        return proPrecoCusto;
    }

    public void setProPrecoCusto(BigDecimal proPrecoCusto) {
        this.proPrecoCusto = proPrecoCusto;
    }

    public BigDecimal getProPrecoVenda() {
        return proPrecoVenda;
    }

    public void setProPrecoVenda(BigDecimal proPrecoVenda) {
        this.proPrecoVenda = proPrecoVenda;
    }

    public Integer getProQuantidadeEstoque() {
        return proQuantidadeEstoque;
    }

    public void setProQuantidadeEstoque(Integer proQuantidadeEstoque) {
        this.proQuantidadeEstoque = proQuantidadeEstoque;
    }

    public String getProCategoria() {
        return proCategoria;
    }

    public void setProCategoria(String proCategoria) {
        this.proCategoria = proCategoria;
    }

    public String getProCodigoBarras() {
        return proCodigoBarras;
    }

    public void setProCodigoBarras(String proCodigoBarras) {
        this.proCodigoBarras = proCodigoBarras;
    }

    public String getProMarca() {
        return proMarca;
    }

    public void setProMarca(String proMarca) {
        this.proMarca = proMarca;
    }

    public String getProUnidadeMedida() {
        return proUnidadeMedida;
    }

    public void setProUnidadeMedida(String proUnidadeMedida) {
        this.proUnidadeMedida = proUnidadeMedida;
    }

    public String getProAtivo() {
        return proAtivo;
    }

    public void setProAtivo(String proAtivo) {
        this.proAtivo = proAtivo;
    }

    public LocalDateTime getProDataCadastro() {
        return proDataCadastro;
    }

    public void setProDataCadastro(LocalDateTime proDataCadastro) {
        this.proDataCadastro = proDataCadastro;
    }

    public LocalDateTime getProDataAtualizacao() {
        return proDataAtualizacao;
    }

    public void setProDataAtualizacao(LocalDateTime proDataAtualizacao) {
        this.proDataAtualizacao = proDataAtualizacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}