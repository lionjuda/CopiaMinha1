package org.example.services;

import org.example.dto.CompraDto;
import org.example.dto.VendaDto;
import org.example.entities.*;
import org.example.repositories.ClienteRepository;
import org.example.repositories.FormaPagamentoRepository;
import org.example.repositories.ProdutoRepository;
import org.example.repositories.VendaRepository;
import org.example.services.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    public List<VendaDto> getAll(){
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VendaDto findDTOById(Long id){
        Venda venda = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toDTO(venda);
    }

    public Venda insert(VendaDto dto) {
        Venda venda = new Venda();

        Cliente cliente = clienteRepository.findById(dto.getCliId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getCliId()));

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(dto.getFpgId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getFpgId()));

        venda.setCliente(cliente);
        venda.setFormaPagamento(formaPagamento);
        venda.setVendaCodigo(dto.getVendaCodigo());
        venda.setVendaId(dto.getVendaId());
        venda.setVendaData(dto.getVendaData());

        List<Compra> compras = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CompraDto compraDTO : dto.getCompras()) {
            Produto produto = produtoRepository.findById(compraDTO.getProId())
                    .orElseThrow(() -> new ResourceNotFoundException(compraDTO.getProId()));

            produtoService.reduzirEstoque(produto.getProId(), compraDTO.getCompraQuantidade());

            Compra compra = new Compra();
            compra.setProduto(produto);
            compra.setCompraQuantidade(compraDTO.getCompraQuantidade());
            compra.setCompraPrecoVenda(compraDTO.getCompraPrecoVenda());
            compra.setVenda(venda);

            BigDecimal subtotal = compraDTO.getCompraPrecoVenda()
                    .multiply(BigDecimal.valueOf(compraDTO.getCompraQuantidade()));
            total = total.add(subtotal);

            compras.add(compra);
        }

        venda.setCompras(compras);
        venda.setVendaValorTotal(total);

        return repository.save(venda);

    }

    public VendaDto toDTO(Venda venda){
        VendaDto dto = new VendaDto();

        dto.setVendaId(venda.getVendaId());
        dto.setVendaCodigo(venda.getVendaCodigo());
        dto.setVendaData(venda.getVendaData());
        dto.setCliId(venda.getCliente().getCliId());
        dto.setFpgId(venda.getFormaPagamento().getFpgId());
        dto.setVendaValorTotal(venda.getVendaValorTotal());

        List<CompraDto> comprasDTO = venda.getCompras()
                .stream()
                .map(compra -> {
                    CompraDto compraDto = new CompraDto();
                    compraDto.setProId(compra.getProduto().getProId());
                    compraDto.setCompraQuantidade(compra.getCompraQuantidade());
                    compraDto.setCompraPrecoVenda(compra.getCompraPrecoVenda());
                    return compraDto;
                })
                .collect(Collectors.toList());

        dto.setCompras(comprasDTO);

        return dto;
    }
}