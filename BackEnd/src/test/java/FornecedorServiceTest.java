import org.example.dto.FornecedorDto;
import org.example.entities.Contato;
import org.example.entities.Endereco;
import org.example.entities.Fornecedor;
import org.example.repositories.FornecedorRepository;
import org.example.repositories.EnderecoRepository;
import org.example.services.FornecedorService;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.services.exeptions.ValueBigForAtributeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FornecedorServiceTest {

    @InjectMocks
    private FornecedorService fornecedorService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fornecedor = new Fornecedor(1L, "Fantasia", "12345678000199", "Razão Social");

        Endereco endereco = new Endereco();
        endereco.setEndRua("Rua A");
        endereco.setEndNumero("123");
        endereco.setEndCidade("Cidade");
        endereco.setEndCep("12345-678");
        endereco.setEndEstado("Estado");
        endereco.setEndFornecedor(fornecedor);

        Contato contato = new Contato();
        contato.setConCelular("99999-9999");
        contato.setConTelefoneComercial("1111-1111");
        contato.setConEmail("email@teste.com");
        contato.setConFornecedor(fornecedor);

        fornecedor.setEnderecos(Arrays.asList(endereco));
        fornecedor.setContatos(Arrays.asList(contato));
    }

    @Test
    void testFindByIdComSucesso() {
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));

        Fornecedor result = fornecedorService.findById(1L);

        assertNotNull(result);
        assertEquals("Fantasia", result.getForNomeFantasia());
        assertEquals("12345678000199", result.getForCnpj());
        verify(fornecedorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdComFalha() {
        when(fornecedorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fornecedorService.findById(2L));

        verify(fornecedorRepository, times(1)).findById(2L);
    }

    @Test
    void testInsertComSucesso() {
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        Fornecedor result = fornecedorService.insert(fornecedor);

        assertNotNull(result);
        assertEquals("Fantasia", result.getForNomeFantasia());
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
        verify(enderecoRepository, times(1)).saveAll(fornecedor.getEnderecos());
    }

    @Test
    void testInsertComViolacaoDeIntegridade() {
        when(fornecedorRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ValueBigForAtributeException.class, () -> fornecedorService.insert(fornecedor));

        verify(fornecedorRepository, times(1)).save(any());
    }

    @Test
    void testUpdateComSucesso() {
        FornecedorDto dto = new FornecedorDto();
        dto.setForRazaoSocial("Nova Razão");
        dto.setForCnpj("98765432000188");
        dto.setEndRua("Rua Nova");
        dto.setEndNumero("456");
        dto.setEndCidade("Cidade Nova");
        dto.setEndCep("88888-000");
        dto.setEndEstado("Estado Novo");
        dto.setConCelular("88888-8888");
        dto.setConTelefoneComercial("2222-2222");
        dto.setConEmail("novo@email.com");

        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        Fornecedor result = fornecedorService.update(1L, dto);

        assertNotNull(result);
        assertEquals("Nova Razão", result.getForRazaoSocial());
        assertEquals("98765432000188", result.getForCnpj());
        assertEquals("Rua Nova", result.getEnderecos().get(0).getEndRua());
        assertEquals("88888-8888", result.getContatos().get(0).getConCelular());

        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void testDeleteFornecedorComSucesso() {
        doNothing().when(fornecedorRepository).deleteById(1L);

        assertDoesNotThrow(() -> fornecedorService.deleteFornecedor(1L));
        verify(fornecedorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFornecedorComFalha() {
        doThrow(new EmptyResultDataAccessException(1)).when(fornecedorRepository).deleteById(99L);

        assertThrows(ResourceNotFoundException.class, () -> fornecedorService.deleteFornecedor(99L));
        verify(fornecedorRepository, times(1)).deleteById(99L);
    }

    @Test
    void testFromDto() {
        FornecedorDto dto = new FornecedorDto();
        dto.setForNomeFantasia("Fantasia");
        dto.setForRazaoSocial("Razão");
        dto.setForCnpj("12345678000199");
        dto.setEndRua("Rua Teste");
        dto.setEndNumero("100");
        dto.setEndCidade("Cidade");
        dto.setEndCep("00000-000");
        dto.setEndEstado("Estado");
        dto.setConCelular("99999-0000");
        dto.setConTelefoneComercial("3333-3333");
        dto.setConEmail("");

        Fornecedor fornec = fornecedorService.fromDTO(dto);

        assertNotNull(fornec);
        assertEquals("Fantasia", fornec.getForNomeFantasia());
        assertEquals("Cidade", fornec.getEnderecos().get(0).getEndCidade());
        assertEquals("", fornec.getContatos().get(0).getConEmail());
    }

    @Test
    void testToNewDto() {
        FornecedorDto dto = fornecedorService.toNewDto(fornecedor);

        assertNotNull(dto);
        assertEquals("Fantasia", dto.getForNomeFantasia());
        assertEquals("Razão Social", dto.getForRazaoSocial());
        assertEquals("Rua A", dto.getEndRua());
        assertEquals("99999-9999", dto.getConCelular());
    }
}