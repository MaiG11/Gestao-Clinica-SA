package com.gestaoclinica.controller;

import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    // Listar consultas
    @GetMapping
    public List<Consulta> listar() {
        return service.listar();
    }

    // Salvar consulta
    @PostMapping
    public Consulta salvar(@RequestBody Consulta consulta) {
        return service.salvar(consulta);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public Consulta buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Excluir consulta
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    
    }
}

