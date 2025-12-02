package com.gestaoclinica.controller;

import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    // Carregar lista de pacientes
    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    // Salvar paciente
    @PostMapping
    public Paciente salvar(@RequestBody Paciente paciente) {
        return service.salvar(paciente);
    }

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Excluir paciente por ID
    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}
