package com.implancec.controller;

import java.util.List;
import java.util.Optional;

import com.implancec.controller.exception.AfiliadoNotFoundException;
import com.implancec.dao.AfiliadoRepository;
import com.implancec.dto.Afiliado;
import com.implancec.service.AfiliadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AfiliadoController {

    private final AfiliadoRepository repository;
    private final AfiliadoService service;

    @Autowired
    AfiliadoController(AfiliadoRepository repository, AfiliadoService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/afiliados")
    List<Afiliado> all(Optional<String> nombre,
                       Optional<Long> id,
                       Optional<Integer> tipo) {
        return service.getAfiliadoList(nombre, id, tipo);
    }

    @PostMapping("/afiliados")
    Afiliado newAfiliado(@RequestBody Afiliado newAfiliado) {
        return repository.save(newAfiliado);
    }

    // Single item
    @GetMapping("/afiliados/{id}")
    Afiliado one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new AfiliadoNotFoundException(id));
    }

    @PutMapping("/afiliados/{id}")
    Afiliado replaceAfiliado(@RequestBody Afiliado newAfiliado, @PathVariable Long id) {

        return repository.findById(id)
                .map(afiliado -> {
                    afiliado.setStreet(newAfiliado.getStreet());
                    afiliado.setCodPost(newAfiliado.getCodPost());
                    afiliado.setDob(newAfiliado.getDob());
                    afiliado.setColonia(newAfiliado.getColonia());
                    afiliado.setEmail(newAfiliado.getEmail());
                    afiliado.setExtnum(newAfiliado.getExtnum());
                    afiliado.setIntnum(newAfiliado.getIntnum());
                    afiliado.setGeoLat(newAfiliado.getGeoLat());
                    afiliado.setGeoLng(newAfiliado.getGeoLng());
                    afiliado.setName(newAfiliado.getName());
                    afiliado.setTipo(newAfiliado.getTipo());
                    return repository.save(afiliado);
                })
                .orElseGet(() -> {
                    newAfiliado.setId(id);
                    return repository.save(newAfiliado);
                });
    }

    @DeleteMapping("/afiliados/{id}")
    void deleteAfiliado(@PathVariable Long id) {
        repository.deleteById(id);
    }
}