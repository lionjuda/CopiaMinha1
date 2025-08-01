package org.example.resources;

import org.example.dto.VendaDto;
import org.example.entities.Venda;
import org.example.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/vendas")
public class VendaResource {

    @Autowired
    private VendaService service;

    @PostMapping
    public ResponseEntity<VendaDto> insert(@Valid @RequestBody VendaDto dto) {
        Venda novaVenda = service.insert(dto);
        VendaDto responseDTO = service.toDTO(novaVenda);
        URI uri = URI.create("/vendas/" + novaVenda.getVendaId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<VendaDto>> findAll(){
        List<VendaDto> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDto> findById(@PathVariable Long id) {
        VendaDto dto = service.findDTOById(id);
        return ResponseEntity.ok(dto);
    }
}