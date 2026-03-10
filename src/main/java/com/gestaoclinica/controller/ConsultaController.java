package com.gestaoclinica.controller;

import com.gestaoclinica.dto.ConsultaRequestDTO;
import com.gestaoclinica.dto.ConsultaResponseDTO;
import com.gestaoclinica.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    // Construtor para injetar o service
    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    // Listar todas as consultas
    @GetMapping
    public List<ConsultaResponseDTO> listar() {
        // Service já retorna a lista de DTOs, controller só repassa
        return service.listar();
    }

    // Salvar nova consulta
    @PostMapping
    public ConsultaResponseDTO salvar(@RequestBody ConsultaRequestDTO dto) {
        // Recebe o DTO de requisição e retorna o DTO de resposta
        return service.salvar(dto);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ConsultaResponseDTO buscar(@PathVariable Long id) {
        // Service retorna a entidade, transformamos em DTO
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

    // Excluir consulta por ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPorId(id);
    }
}