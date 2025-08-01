package org.example.services;

import org.example.dto.ClienteDto;
import org.example.entities.Cliente;
import org.example.entities.Contato;
import org.example.entities.Endereco;
import org.example.repositories.ClienteRepository;
import org.example.repositories.EnderecoRepository;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.services.exeptions.ValueBigForAtributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Cliente insert(Cliente obj) {
        try {
            obj.setCliId(null);
            obj = repository.save(obj);
            enderecoRepository.saveAll(obj.getEnderecos());
            return obj;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        }
    }

    public Cliente update(Long id, ClienteDto objDto) {
        try {
            Cliente entity = findById(id);

            // Atualiza os dados do cliente
            entity.setCliNome(objDto.getCliNome());
            entity.setCliCpf(objDto.getCliCpf());

            // Atualiza o endereço do cliente (assumindo que há apenas um)
            if (entity.getEnderecos().isEmpty()) {
                throw new RuntimeException("Cliente não possui endereço cadastrado.");
            }

            Endereco endereco = entity.getEnderecos().get(0);
            endereco.setEndRua(objDto.getEndRua());
            endereco.setEndNumero(objDto.getEndNumero());
            endereco.setEndCidade(objDto.getEndCidade());
            endereco.setEndCep(objDto.getEndCep());
            endereco.setEndEstado(objDto.getEndEstado());

            // Atualiza o contato (assumindo que há apenas um)
            if (entity.getContatos().isEmpty()) {
                throw new RuntimeException("Cliente não possui contato cadastrado.");
            }

            Contato contato = entity.getContatos().get(0);
            contato.setConCelular(objDto.getConCelular());
            contato.setConTelefoneComercial(objDto.getConTelefoneComercial());
            contato.setConEmail(objDto.getConEmail());

            // Salva as alterações
            repository.save(entity);
            return entity;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        }
    }

    public void deleteCliente(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Cliente fromDTO(ClienteDto objDto) {
        Cliente cliente = new Cliente(null, objDto.getCliNome(), objDto.getCliCpf());

        Endereco endereco = new Endereco(null, cliente, objDto.getEndRua(), objDto.getEndNumero(),
                objDto.getEndCidade(), objDto.getEndCep(), objDto.getEndEstado());

        Contato contato = new Contato(null, cliente, objDto.getConCelular(),
                objDto.getConTelefoneComercial(), objDto.getConEmail());

        cliente.getEnderecos().add(endereco);
        cliente.getContatos().add(contato);

        return cliente;
    }

    public ClienteDto toNewDto(Cliente obj) {
        ClienteDto dto = new ClienteDto();

        // Atributos comuns
        dto.setCliId(obj.getCliId());
        dto.setCliNome(obj.getCliNome());
        dto.setCliCpf(obj.getCliCpf());

        // Atributos de endereço (assumindo que há pelo menos um)
        if (!obj.getEnderecos().isEmpty()) {
            Endereco endereco = obj.getEnderecos().get(0);
            dto.setEndRua(endereco.getEndRua());
            dto.setEndNumero(endereco.getEndNumero());
            dto.setEndCidade(endereco.getEndCidade());
            dto.setEndCep(endereco.getEndCep());
            dto.setEndEstado(endereco.getEndEstado());
        }

        // Atributos de contato (assumindo que há pelo menos um)
        if (!obj.getContatos().isEmpty()) {
            Contato contato = obj.getContatos().get(0);
            dto.setConCelular(contato.getConCelular());
            dto.setConTelefoneComercial(contato.getConTelefoneComercial());
            dto.setConEmail(contato.getConEmail());
        }

        return dto;
    }
}