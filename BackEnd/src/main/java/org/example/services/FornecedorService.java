package org.example.services;

import org.example.dto.FornecedorDto;
import org.example.entities.Fornecedor;
import org.example.entities.Contato;
import org.example.entities.Endereco;
import org.example.repositories.FornecedorRepository;
import org.example.repositories.EnderecoRepository;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.services.exeptions.ValueBigForAtributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Fornecedor> findAll() {
        return repository.findAll();
    }

    public Fornecedor findById(Long id) {
        Optional<Fornecedor> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Fornecedor insert(Fornecedor obj) {
        try {
            obj.setForId(null);
            obj = repository.save(obj);
            enderecoRepository.saveAll(obj.getEnderecos());
            return obj;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        }
    }

    @Transactional
    public Fornecedor update(Long id, FornecedorDto objDto) {
        try {
            Fornecedor entity = findById(id);

            // Atualiza os dados do fornecedor
            entity.setForRazaoSocial(objDto.getForRazaoSocial());
            entity.setForNomeFantasia(objDto.getForNomeFantasia());
            entity.setForCnpj(objDto.getForCnpj());

            // Atualiza ou adiciona endereço
            if (entity.getEnderecos() != null && !entity.getEnderecos().isEmpty()) {
                Endereco endereco = entity.getEnderecos().get(0);
                endereco.setEndRua(objDto.getEndRua());
                endereco.setEndNumero(objDto.getEndNumero());
                endereco.setEndCidade(objDto.getEndCidade());
                endereco.setEndCep(objDto.getEndCep());
                endereco.setEndEstado(objDto.getEndEstado());
            } else {
                Endereco novoEndereco = new Endereco(null, entity,
                        objDto.getEndRua(), objDto.getEndNumero(),
                        objDto.getEndCidade(), objDto.getEndCep(),
                        objDto.getEndEstado());
                entity.getEnderecos().add(novoEndereco);
            }

            // Atualiza ou adiciona contato
            if (entity.getContatos() != null && !entity.getContatos().isEmpty()) {
                Contato contato = entity.getContatos().get(0);
                contato.setConCelular(objDto.getConCelular());
                contato.setConTelefoneComercial(objDto.getConTelefoneComercial());
                contato.setConEmail(objDto.getConEmail());
            } else {
                Contato novoContato = new Contato(null, entity,
                        objDto.getConCelular(),
                        objDto.getConTelefoneComercial(),
                        objDto.getConEmail());
                entity.getContatos().add(novoContato);
            }

            repository.save(entity);
            return entity;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID não encontrado: " + id);
        }
    }

    public void deleteFornecedor(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Fornecedor fromDTO(FornecedorDto objDto) {
        Fornecedor fornec = new Fornecedor(null,
                objDto.getForNomeFantasia(),
                objDto.getForCnpj(),
                objDto.getForRazaoSocial());

        Endereco ender = new Endereco(null, fornec,
                objDto.getEndRua(), objDto.getEndNumero(),
                objDto.getEndCidade(), objDto.getEndCep(),
                objDto.getEndEstado());

        Contato contato = new Contato(null, fornec,
                objDto.getConCelular(), objDto.getConTelefoneComercial(),
                objDto.getConEmail());

        fornec.getEnderecos().add(ender);
        fornec.getContatos().add(contato);

        return fornec;
    }

    public FornecedorDto toNewDto(Fornecedor obj) {
        FornecedorDto dto = new FornecedorDto();

        dto.setForId(obj.getForId());
        dto.setForNomeFantasia(obj.getForNomeFantasia());
        dto.setForRazaoSocial(obj.getForRazaoSocial());
        dto.setForCnpj(obj.getForCnpj());

        if (!obj.getEnderecos().isEmpty()) {
            Endereco endereco = obj.getEnderecos().get(0);
            dto.setEndRua(endereco.getEndRua());
            dto.setEndNumero(endereco.getEndNumero());
            dto.setEndCidade(endereco.getEndCidade());
            dto.setEndCep(endereco.getEndCep());
            dto.setEndEstado(endereco.getEndEstado());
        }

        if (!obj.getContatos().isEmpty()) {
            Contato contato = obj.getContatos().get(0);
            dto.setConCelular(contato.getConCelular());
            dto.setConTelefoneComercial(contato.getConTelefoneComercial());
            dto.setConEmail(contato.getConEmail());
        }

        return dto;
    }
}