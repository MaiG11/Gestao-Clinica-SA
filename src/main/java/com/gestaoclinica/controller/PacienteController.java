package com.gestaoclinica.controller;

import com.gestaoclinica.dto.PacienteRequestDTO;
import com.gestaoclinica.dto.PacienteResponseDTO;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public PacienteResponseDTO salvar(@RequestBody PacienteRequestDTO dadosPaciente) {
        return service.salvar(dadosPaciente);
    }

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Excluir paciente por ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}