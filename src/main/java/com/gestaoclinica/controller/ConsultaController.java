package com.gestaoclinica.controller;

import com.gestaoclinica.dto.ConsultaRequestDTO;
import com.gestaoclinica.dto.ConsultaResponseDTO;
import com.gestaoclinica.service.ConsultaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    // Listar todas as consultas
    @GetMapping
    public List<ConsultaResponseDTO> listar() {
        return service.listar();
    }

    // Salvar nova consulta
    @PostMapping
    public ConsultaResponseDTO salvar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return service.salvar(dto);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ConsultaResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorIdDTO(id);
    }

    // Excluir consulta
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
    @GetMapping("/paciente/{id}")
    public List<ConsultaResponseDTO> listarPorPaciente(@PathVariable Long id) {
        return service.listarPorPaciente(id);
}
}