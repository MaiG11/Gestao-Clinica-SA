package com.gestaoclinica.controller;

import com.gestaoclinica.dto.ConsultaRequestDTO;
import com.gestaoclinica.dto.ConsultaResponseDTO;
import com.gestaoclinica.service.ConsultaService;

import jakarta.validation.Valid; // Necessário para validar DTO
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

    // Salvar nova consulta com validação
    @PostMapping
    public ConsultaResponseDTO salvar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return service.salvar(dto);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ConsultaResponseDTO buscar(@PathVariable Long id) {
        var consulta = service.buscarPorId(id);
        return new ConsultaResponseDTO(
                consulta.getIdConsulta(),
                consulta.getDataConsulta(),
                consulta.getHorario(),
                consulta.getPaciente().getIdPaciente(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getIdMedico(),
                consulta.getMedico().getNome(),
                consulta.getStatus(),
                consulta.getTipoConsulta(),
                consulta.getSintomas(),
                consulta.getDiagnostico(),
                consulta.getMedicacaoPrescrita(),
                consulta.getRetornoNecessario(),
                consulta.getDataRetorno(),
                consulta.getAlergiasRelevantes(),
                consulta.getObservacoes()
        );
    }

    // Excluir consulta
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}