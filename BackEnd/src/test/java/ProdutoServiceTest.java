import org.example.dto.ProdutoDto;
import org.example.entities.Fornecedor;
import org.example.entities.Produto;
import org.example.repositories.FornecedorRepository;
import org.example.repositories.ProdutoRepository;
import org.example.services.ProdutoService;
import org.example.services.exeptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService; //injeta o serviço com os mocks

    @Mock
    private ProdutoRepository repository; //mock do repositório de produtos

    @Mock
    private FornecedorRepository fornecedorRepository; //mock do repositório de fornecedores

    //objetos reutilizados
    private Produto produto;
    private Fornecedor fornecedor;
    private ProdutoDto produtoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //inicaliza os mocks antes de cada teste

        //criação do fornecedor
        fornecedor = new Fornecedor();
        fornecedor.setForId(1L);
        fornecedor.setForNomeFantasia("Padaria Central");
        fornecedor.setForCnpj("12345678000199");
        fornecedor.setForRazaoSocial("Padaria Central Ltda");

        //criação do produto
        produto = new Produto();
        produto.setProId(1L);
        produto.setProNome("Pão Francês");
        produto.setProDescricao("Pão francês fresquinho");
        produto.setProPrecoCusto(new BigDecimal("0.30"));
        produto.setProPrecoVenda(new BigDecimal("0.50"));
        produto.setProQuantidadeEstoque(100);
        produto.setProCategoria("Padaria");
        produto.setProCodigoBarras("7891234567890");
        produto.setProMarca("Nenhuma");
        produto.setProUnidadeMedida("Unidade");
        produto.setProAtivo("Sim");
        produto.setProDataCadastro(LocalDateTime.now());
        produto.setProDataAtualizacao(LocalDateTime.now());
        produto.setFornecedor(fornecedor);

        //criação do dto dos produtos
        produtoDto = new ProdutoDto();
        produtoDto.setProId(1L);
        produtoDto.setProNome("Pão Francês");
        produtoDto.setProDescricao("Pão francês fresquinho");
        produtoDto.setProPrecoCusto(new BigDecimal("0.30"));
        produtoDto.setProPrecoVenda(new BigDecimal("0.50"));
        produtoDto.setProQuantidadeEstoque(100);
        produtoDto.setProCategoria("Padaria");
        produtoDto.setProCodigoBarras("7891234567890");
        produtoDto.setProMarca("Nenhuma");
        produtoDto.setProUnidadeMedida("Unidade");
        produtoDto.setProAtivo("Sim");
        produtoDto.setProDataCadastro(LocalDateTime.now());
        produtoDto.setProDataAtualizacao(LocalDateTime.now());
        produtoDto.setForId(1L);
    }

    //teste de inserção
    @Test
    void testInsertComSucesso() {
        //define o comportamento esperado dos mocks
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        //chama o metodo do serviço
        ProdutoDto resultado = produtoService.insert(produtoDto);

        //verifica o resultado
        assertNotNull(resultado);
        assertEquals("Pão Francês", resultado.getProNome());

        //verifica se os metodos do mocks foram chamados
        verify(fornecedorRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Produto.class));
    }

    //teste de atualização com sucesso
    @Test
    void testUpdateComSucesso() {
        ProdutoDto novoDto = new ProdutoDto();
        novoDto.setProId(1L);
        novoDto.setProNome("Bolo de Cenoura");
        novoDto.setProDescricao("Bolo com cobertura de chocolate");
        novoDto.setProPrecoCusto(new BigDecimal("5.00"));
        novoDto.setProPrecoVenda(new BigDecimal("8.00"));
        novoDto.setProQuantidadeEstoque(10);
        novoDto.setProCategoria("Confeitaria");
        novoDto.setProCodigoBarras("7890000000001");
        novoDto.setProMarca("Doce Sabor");
        novoDto.setProUnidadeMedida("Unidade");
        novoDto.setProAtivo("Sim");
        novoDto.setProDataCadastro(LocalDateTime.now());
        novoDto.setProDataAtualizacao(LocalDateTime.now());
        novoDto.setForId(1L);

        //simula produto existente e fornecedor
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDto atualizado = produtoService.update(1L, novoDto);

        assertNotNull(atualizado);
        assertEquals("Bolo de Cenoura", atualizado.getProNome());
        verify(repository, times(1)).save(any(Produto.class));
    }

    //teste de atualização com falha
    @Test
    void testUpdateComFalha() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtoService.update(99L, produtoDto));
        verify(repository, never()).save(any(Produto.class));
    }

    //teste de exclusão
    @Test
    void testDeleteComSucesso() {
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> produtoService.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}