package com.gestaoclinica.controller;

import com.gestaoclinica.dto.MedicoRequestDTO;
import com.gestaoclinica.dto.MedicoResponseDTO;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.service.MedicoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medico> listar() {
        return service.listar();
    }

    // Salvar médico com validação
    @PostMapping
    public MedicoResponseDTO salvar(@Valid @RequestBody MedicoRequestDTO medico) {
        return service.salvar(medico);
    }

    @GetMapping("/{id}")
    public Medico buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/especialidade/{especialidade}")
    public List<Medico> buscarPorEspecialidade(@PathVariable String especialidade) {
        return service.buscarPorEspecialidade(especialidade);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}