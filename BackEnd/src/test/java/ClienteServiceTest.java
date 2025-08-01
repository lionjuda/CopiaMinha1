import org.example.dto.ClienteDto;
import org.example.entities.Cliente;
import org.example.entities.Contato;
import org.example.entities.Endereco;
import org.example.repositories.ClienteRepository;
import org.example.repositories.EnderecoRepository;
import org.example.services.ClienteService;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setCliId(1L);
        cliente.setCliNome("Maria Silva");
        cliente.setCliCpf("12334556789");

        Endereco endereco = new Endereco(1L, cliente, "Rua A", "123", "Cidade", "12345-678", "PR");
        Contato contato = new Contato(1L, cliente, "99999-9999", "3344-5566", "maria@gmail.com");

        cliente.setEnderecos(Arrays.asList(endereco));
        cliente.setContatos(Arrays.asList(contato));
    }

    @Test
    void deveBuscarClientePorIdExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Maria Silva", resultado.getCliNome());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.findById(2L));
    }

    @Test
    void deveConverterDeDtoParaEntidade() {
        ClienteDto dto = new ClienteDto();
        dto.setCliNome("Carlos");
        dto.setCliCpf("12334556789");
        dto.setEndRua("Rua B");
        dto.setEndNumero("456");
        dto.setEndCidade("Cidade X");
        dto.setEndCep("99999-000");
        dto.setEndEstado("SP");
        dto.setConCelular("98888-0000");
        dto.setConTelefoneComercial("3131-3131");
        dto.setConEmail("carlos@email.com");

        Cliente convertido = clienteService.fromDTO(dto);

        assertEquals("Carlos", convertido.getCliNome());
        assertEquals("Rua B", convertido.getEnderecos().get(0).getEndRua());
        assertEquals("98888-0000", convertido.getContatos().get(0).getConCelular());
    }

    @Test
    void deveConverterDeEntidadeParaDto() {
        ClienteDto dto = clienteService.toNewDto(cliente);

        assertEquals("Maria Silva", dto.getCliNome());
        assertEquals("Rua A", dto.getEndRua());
        assertEquals("99999-9999", dto.getConCelular());
    }

    @Test
    void deveInserirClienteComSucecsso() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.insert(cliente);

        assertNotNull(resultado);
        assertEquals("Maria Silva", resultado.getCliNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(enderecoRepository, times(1)).saveAll(cliente.getEnderecos());
    }

    @Test
    void deveLancarExcecaoAoInserirComViolacaoDeIntegridade() {
        when(clienteRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ValueBigForAtributeException.class, () -> clienteService.insert(cliente));
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        ClienteDto dto = new ClienteDto();
        dto.setCliNome("Carlos Silva");
        dto.setCliCpf("98765432100");
        dto.setEndRua("Rua Nova");
        dto.setEndNumero("999");
        dto.setEndCidade("Cidade Nova");
        dto.setEndCep("00000-000");
        dto.setEndEstado("RJ");
        dto.setConCelular("90000-0000");
        dto.setConTelefoneComercial("5555-5555");
        dto.setConEmail("carlos@novo.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente atualizado = clienteService.update(1L, dto);

        assertNotNull(atualizado);
        assertEquals("Carlos Silva", atualizado.getCliNome());
        assertEquals("Rua Nova", atualizado.getEnderecos().get(0).getEndRua());
        assertEquals("90000-0000", atualizado.getContatos().get(0).getConCelular());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.deleteCliente(1L));
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        doThrow(new EmptyResultDataAccessException(1)).when(clienteRepository).deleteById(99L);

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deleteCliente(99L));
    }
}