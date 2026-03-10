package com.gestaoclinica.controller;

import com.gestaoclinica.dto.MedicoRequestDTO;
import com.gestaoclinica.dto.MedicoResponseDTO;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.service.MedicoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public MedicoResponseDTO salvar(@RequestBody MedicoRequestDTO medico) {
        return service.salvar(medico);
    }

    // Buscar médico por ID
    @GetMapping("/{id}")
    public Medico buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Buscar médicos por especialidade
    @GetMapping("/especialidade/{especialidade}")
    public List<Medico> buscarPorEspecialidade(@PathVariable String especialidade) {
        return service.buscarPorEspecialidade(especialidade);
    }

    // Excluir médico
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}