package org.example.services;

import org.example.entities.Contato;
import org.example.repositories.ContatoRepository;
import org.example.services.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    public List<Contato> getAll() {
        return repository.findAll();
    }

    public Contato findById(Long id) {
        Optional<Contato> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Contato insert(Contato contato) {
        return repository.save(contato);
    }

    public boolean update(Long id, Contato contato) {
        Optional<Contato> optionalContato = repository.findById(id);
        if (optionalContato.isPresent()) {
            Contato contatoSistema = optionalContato.get();
            contatoSistema.setConCelular(contato.getConCelular());
            contatoSistema.setConEmail(contato.getConEmail());
            contatoSistema.setConTelefoneComercial(contato.getConTelefoneComercial());
            repository.save(contatoSistema);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}