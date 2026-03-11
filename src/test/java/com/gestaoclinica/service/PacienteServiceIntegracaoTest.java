package com.gestaoclinica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gestaoclinica.dto.PacienteRequestDTO;
import com.gestaoclinica.dto.PacienteResponseDTO;
import com.gestaoclinica.exception.exception_paciente.PacienteCampoObrigatorioException;
import com.gestaoclinica.exception.exception_paciente.PacienteCpfJaCadastradoException;
import com.gestaoclinica.exception.exception_paciente.PacienteNotFoundException;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.PacienteRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PacienteServiceIntegracaoTest {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Salvamento de paciente com sucesso")
    public void deveSalvarPacienteComSucesso(){

        PacienteRequestDTO paciente = new PacienteRequestDTO(
                "Wellington Braz",
                null,
                "1234567899",
                null,
                null,
                null,
                null
        );

        PacienteResponseDTO pacienteSalvo = pacienteService.salvar(paciente);

        assertNotNull(pacienteSalvo.idPaciente());
        assertEquals("Wellington Braz", pacienteSalvo.nome());
        assertEquals("1234567899", pacienteSalvo.cpf());
        assertNotNull(pacienteSalvo.dataCadastro());
    }

    @Test
    @DisplayName("Validação de paciente sem nome")
    public void deveLancarExcecaoQuandoPacienteNaoTiverNome(){

        PacienteRequestDTO paciente = new PacienteRequestDTO(
                null,
                null,
                "1234567899",
                null,
                null,
                null,
                null
        );

        assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);
        });

        
    }

    @Test
    @DisplayName("Validação de CPF vazio")
    public void deveLancarExcecaoQuandoCpfForVazio(){

        PacienteRequestDTO paciente = new PacienteRequestDTO(
                "Maira",
                null,
                "",
                null,
                null,
                null,
                null
        );

        assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);
        });
    }

    @Test
    @DisplayName("Validação de CPF nulo")
    public void deveLancarExcecaoQuandoCpfForNulo(){

        PacienteRequestDTO paciente = new PacienteRequestDTO(
                "Maira",
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);
        });
    }

    @Test
    @DisplayName("Validação de CPF duplicado")
    public void deveLancarExcecaoQuandoCpfJaExistir(){

        PacienteRequestDTO paciente1 = new PacienteRequestDTO(
                "Maira",
                null,
                "12345678900",
                null,
                null,
                null,
                null
        );

        pacienteService.salvar(paciente1);

        PacienteRequestDTO paciente2 = new PacienteRequestDTO(
                "João",
                null,
                "12345678900",
                null,
                null,
                null,
                null
        );

        assertThrows(PacienteCpfJaCadastradoException.class, () -> {
            pacienteService.salvar(paciente2);
        });
    }

    @Test
    @DisplayName("Paciente não encontrado por id")
    public void deveLancarExcecaoQuandoIdNaoExistir(){

        assertThrows(PacienteNotFoundException.class, () -> {
            pacienteService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Exclusão de paciente por id")
    void deveExcluirPacienteQuandoIdExistir(){

        PacienteRequestDTO paciente = new PacienteRequestDTO(
                "Maira",
                null,
                "12345678900",
                null,
                null,
                null,
                null
        );

        PacienteResponseDTO salvo = pacienteService.salvar(paciente);

        Long id = salvo.idPaciente();

        pacienteService.excluirPorId(id);

        assertFalse(pacienteRepository.existsById(id));
    }

    @Test
    @DisplayName("Busca de pacientes cadastrados")
    void deveListarTodosOsPacientes(){

        PacienteRequestDTO paciente1 = new PacienteRequestDTO(
                "Maira",
                null,
                "12345678900",
                null,
                null,
                null,
                null
        );

        PacienteRequestDTO paciente2 = new PacienteRequestDTO(
                "João",
                null,
                "12345678901",
                null,
                null,
                null,
                null
        );

        pacienteService.salvar(paciente1);
        pacienteService.salvar(paciente2);

        List<Paciente> lista = pacienteService.listar();

        assertNotNull(lista);
        assertEquals(2, lista.size());
    }
}