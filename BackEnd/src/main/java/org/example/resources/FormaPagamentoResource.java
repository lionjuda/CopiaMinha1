package org.example.resources;

import org.example.entities.FormaPagamento;
import org.example.services.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/formaPagamentos")
public class FormaPagamentoResource {

    @Autowired
    private FormaPagamentoService service;

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> findAll(){
        List<FormaPagamento> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> findById(@PathVariable Long id){
        FormaPagamento obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> insert(@RequestBody FormaPagamento formaPagamento){
        FormaPagamento createdFormaPagamento = service.insert(formaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormaPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FormaPagamento formaPagamento){
        if(service.update(id, formaPagamento)) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}