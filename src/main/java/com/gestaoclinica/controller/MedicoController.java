package com.gestaoclinica.controller;

import com.gestaoclinica.model.Medico;
import com.gestaoclinica.service.MedicoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    // Listar médicos
    @GetMapping
    public List<Medico> listar() {
        return service.listar();
    }

    // Salvar médico
    @PostMapping
    public Medico salvar(@RequestBody Medico medico) {
        return service.salvar(medico);
    }

    // Buscar médico por ID
    @GetMapping("/{id}")
    public Medico buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Excluir médico
    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}
