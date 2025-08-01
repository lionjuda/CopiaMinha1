package org.example.services;

import org.example.dto.ProdutoDto;
import org.example.entities.Fornecedor;
import org.example.entities.Produto;
import org.example.repositories.FornecedorRepository;
import org.example.repositories.ProdutoRepository;
import org.example.services.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public void reduzirEstoque(Long produtoId, Integer quantidade) {
        Produto produto = repository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Integer estoqueAtual = produto.getProQuantidadeEstoque();
        if (estoqueAtual == null) {
            throw new IllegalStateException("Estoque do produto está nulo (produtoId = " + produtoId + ")");
        }

        if (quantidade > estoqueAtual) {
            throw new IllegalArgumentException("Estoque insuficiente para o produtoId = " + produtoId);
        }

        produto.setProQuantidadeEstoque(estoqueAtual - quantidade);
        repository.save(produto);
    }

    public List<ProdutoDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDto findDTOById(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toDTO(produto);
    }

    public ProdutoDto insert(ProdutoDto dto) {
        Produto produto = fromDTO(dto);
        produto.setProDataCadastro(LocalDateTime.now());
        produto.setProDataAtualizacao(LocalDateTime.now());
        Produto saved = repository.save(produto);
        return toDTO(saved);
    }

    public ProdutoDto update(Long id, ProdutoDto dto) {
        Produto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        existing.setProNome(dto.getProNome());
        existing.setProDescricao(dto.getProDescricao());
        existing.setProPrecoCusto(dto.getProPrecoCusto());
        existing.setProPrecoVenda(dto.getProPrecoVenda());
        existing.setProQuantidadeEstoque(dto.getProQuantidadeEstoque());
        existing.setProCategoria(dto.getProCategoria());
        existing.setProCodigoBarras(dto.getProCodigoBarras());
        existing.setProMarca(dto.getProMarca());
        existing.setProUnidadeMedida(dto.getProUnidadeMedida());
        existing.setProAtivo(dto.getProAtivo());
        existing.setProDataAtualizacao(LocalDateTime.now());

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getForId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com ID: " + dto.getForId()));
        existing.setFornecedor(fornecedor);

        Produto updated = repository.save(existing);
        return toDTO(updated);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProdutoDto toDTO(Produto produto) {
        ProdutoDto dto = new ProdutoDto();
        dto.setProId(produto.getProId());
        dto.setProNome(produto.getProNome());
        dto.setProDescricao(produto.getProDescricao());
        dto.setProPrecoCusto(produto.getProPrecoCusto());
        dto.setProPrecoVenda(produto.getProPrecoVenda());
        dto.setProQuantidadeEstoque(produto.getProQuantidadeEstoque());
        dto.setProCategoria(produto.getProCategoria());
        dto.setProCodigoBarras(produto.getProCodigoBarras());
        dto.setProMarca(produto.getProMarca());
        dto.setProUnidadeMedida(produto.getProUnidadeMedida());
        dto.setProAtivo(produto.getProAtivo());
        dto.setProDataCadastro(produto.getProDataCadastro());
        dto.setProDataAtualizacao(produto.getProDataAtualizacao());
        dto.setForId(produto.getFornecedor().getForId());
        return dto;
    }

    private Produto fromDTO(ProdutoDto dto) {
        Produto produto = new Produto();
        produto.setProId(dto.getProId());
        produto.setProNome(dto.getProNome());
        produto.setProDescricao(dto.getProDescricao());
        produto.setProPrecoCusto(dto.getProPrecoCusto());
        produto.setProPrecoVenda(dto.getProPrecoVenda());
        produto.setProQuantidadeEstoque(dto.getProQuantidadeEstoque());
        produto.setProCategoria(dto.getProCategoria());
        produto.setProCodigoBarras(dto.getProCodigoBarras());
        produto.setProMarca(dto.getProMarca());
        produto.setProUnidadeMedida(dto.getProUnidadeMedida());
        produto.setProAtivo(dto.getProAtivo());
        produto.setProDataCadastro(dto.getProDataCadastro());
        produto.setProDataAtualizacao(dto.getProDataAtualizacao());

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getForId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com ID: " + dto.getForId()));
        produto.setFornecedor(fornecedor);

        return produto;
    }
}