        package com.gestaoclinica.service;

        import com.gestaoclinica.dto.ConsultaRequestDTO;
        import com.gestaoclinica.dto.ConsultaResponseDTO;
        import com.gestaoclinica.exception.exception_consulta.ConsultaCampoObrigatorioException;
        import com.gestaoclinica.exception.exception_consulta.ConsultaDataInvalidaException;
        import com.gestaoclinica.exception.exception_consulta.ConsultaHorarioIndisponivelException;
        import com.gestaoclinica.exception.exception_consulta.ConsultaNotFoundException;
        import com.gestaoclinica.exception.exception_medico.MedicoNotFoundException;
        import com.gestaoclinica.exception.exception_paciente.PacienteNotFoundException;
        import com.gestaoclinica.model.Consulta;
        import com.gestaoclinica.model.Medico;
        import com.gestaoclinica.model.Paciente;
        import com.gestaoclinica.repository.ConsultaRepository;
        import com.gestaoclinica.repository.MedicoRepository;
        import com.gestaoclinica.repository.PacienteRepository;
        import org.springframework.stereotype.Service;

        import java.time.LocalDate;
        import java.util.List;

            @Service
            public class ConsultaService {

            private final ConsultaRepository consultaRepository;
            private final PacienteRepository pacienteRepository;
            private final MedicoRepository medicoRepository;

            public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
                this.consultaRepository = consultaRepository;
                this.pacienteRepository = pacienteRepository;
                this.medicoRepository = medicoRepository;
            }

        // Salvar nova consulta
        
    public ConsultaResponseDTO salvar(ConsultaRequestDTO dto) {

        if (dto.pacienteId() == null)
            throw new ConsultaCampoObrigatorioException("pacienteId");

        if (dto.medicoId() == null)
            throw new ConsultaCampoObrigatorioException("medicoId");

        if (dto.dataConsulta() == null)
            throw new ConsultaCampoObrigatorioException("dataConsulta");

        if (dto.horario() == null)
            throw new ConsultaCampoObrigatorioException("horario");

        if (dto.retornoNecessario() == null)
            throw new ConsultaCampoObrigatorioException("retornoNecessario");

        if (dto.retornoNecessario() && dto.dataRetorno() == null)
            throw new ConsultaCampoObrigatorioException("dataRetorno");

        // 🔴 CORREÇÃO: não permite data passada nem hoje (somente futuro)
        if (!dto.dataConsulta().isAfter(LocalDate.now())) {
            throw new ConsultaDataInvalidaException();
        }

        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new PacienteNotFoundException(dto.pacienteId()));

        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new MedicoNotFoundException(dto.medicoId()));

        if (consultaRepository.existsByMedicoAndDataConsultaAndHorario(
                medico, dto.dataConsulta(), dto.horario())) {
            throw new ConsultaHorarioIndisponivelException();
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataConsulta(dto.dataConsulta());
        consulta.setHorario(dto.horario());
        consulta.setStatus(dto.status());
        consulta.setTipoConsulta(dto.tipoConsulta());
        consulta.setSintomas(dto.sintomas());
        consulta.setDiagnostico(dto.diagnostico());
        consulta.setMedicacaoPrescrita(dto.medicacaoPrescrita());
        consulta.setRetornoNecessario(dto.retornoNecessario());
        consulta.setDataRetorno(dto.dataRetorno());
        consulta.setAlergiasRelevantes(dto.alergiasRelevantes());
        consulta.setObservacoes(dto.observacoes());

        Consulta consultaSalva = consultaRepository.save(consulta);

        return converterEntidadeParaDTO(consultaSalva);
    }
            public List<ConsultaResponseDTO> listar() {

            List<Consulta> consultas = consultaRepository.findAll();

            return consultas.stream()
                    .map(this::converterEntidadeParaDTO)
                    .toList();

            }
            // Buscar consulta por ID como DTO
            public ConsultaResponseDTO buscarPorIdDTO(Long id) {
                Consulta consulta = consultaRepository.findById(id)
                        .orElseThrow(() -> new ConsultaNotFoundException(id));
                return converterEntidadeParaDTO(consulta);

            }
            public List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId) {

            List<Consulta> consultas = consultaRepository.buscarPorPacienteId(pacienteId);

            if (consultas.isEmpty()) {
                throw new ConsultaNotFoundException(pacienteId);
            }

            return consultas.stream()
                    .map(this::converterEntidadeParaDTO)
                    .toList();

            }
            // Excluir consulta por ID
            public void excluirPorId(Long id) {
                if (!consultaRepository.existsById(id)) {
                    throw new ConsultaNotFoundException(id);
                }
                consultaRepository.deleteById(id);

            }
            // Excluir todas as consultas
            public void excluirTodos() {
                consultaRepository.deleteAll();
                
            }
            // Método privado para mapear Consulta → ConsultaResponseDTO
            private ConsultaResponseDTO converterEntidadeParaDTO(Consulta consulta) {
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
        }