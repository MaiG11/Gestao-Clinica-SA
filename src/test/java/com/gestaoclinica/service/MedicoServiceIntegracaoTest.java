package com.gestaoclinica.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gestaoclinica.dto.MedicoRequestDTO;
import com.gestaoclinica.dto.MedicoResponseDTO;
import com.gestaoclinica.exception.exception_medico.MedicoCampoObrigatorioException;
import com.gestaoclinica.exception.exception_medico.MedicoCpfJaCadastradoException;
import com.gestaoclinica.exception.exception_medico.MedicoCrmJaCadastradoException;
import com.gestaoclinica.exception.exception_medico.MedicoNotFoundException;
import com.gestaoclinica.model.Medico;


import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class MedicoServiceIntegracaoTest {

    @Autowired
    private MedicoService medicoService;

    

    @Test
    @DisplayName("Salvamento de médico com sucesso")
    public void deveSalvarMedicoComSucesso() {

        MedicoRequestDTO medico = new MedicoRequestDTO(
                "Dr João",
                "12345678900",
                "Cardiologia",
                null,
                null,
                "CRM12345",
                null
        );

        MedicoResponseDTO medicoSalvo = medicoService.salvar(medico);

        assertNotNull(medicoSalvo.idMedico());
        assertEquals("Dr João", medicoSalvo.nome());
        assertEquals("12345678900", medicoSalvo.cpf());
    }

    @Test
    @DisplayName("Validação de médico sem nome")
    public void deveLancarExcecaoQuandoMedicoNaoTiverNome() {

        MedicoRequestDTO medico = new MedicoRequestDTO(
                null,
                "12345678900",
                "Cardiologia",
                null,
                null,
                "CRM12345",
                null
        );

        assertThrows(MedicoCampoObrigatorioException.class, () -> {
            medicoService.salvar(medico);
        });
    }

    @Test
    @DisplayName("Validação de CPF duplicado")
    public void deveLancarExcecaoQuandoCpfJaExistir() {

        MedicoRequestDTO medico1 = new MedicoRequestDTO(
                "Dr João",
                "12345678900",
                "Cardiologia",
                null,
                null,
                "CRM12345",
                null
        );

        medicoService.salvar(medico1);

        MedicoRequestDTO medico2 = new MedicoRequestDTO(
                "Dr Pedro",
                "12345678900",
                "Ortopedia",
                null,
                null,
                "CRM67890",
                null
        );

        assertThrows(MedicoCpfJaCadastradoException.class, () -> {
            medicoService.salvar(medico2);
        });
    }

    @Test
    @DisplayName("Validação de CRM duplicado")
    public void deveLancarExcecaoQuandoCrmJaExistir() {

        MedicoRequestDTO medico1 = new MedicoRequestDTO(
                "Dr João",
                "12345678900",
                "Cardiologia",
                null,
                null,
                "CRM12345",
                null
        );

        medicoService.salvar(medico1);

        MedicoRequestDTO medico2 = new MedicoRequestDTO(
                "Dr Pedro",
                "99999999999",
                "Ortopedia",
                null,
                null,
                "CRM12345",
                null
        );

        assertThrows(MedicoCrmJaCadastradoException.class, () -> {
            medicoService.salvar(medico2);
        });
    }

    @Test
    @DisplayName("Médico não encontrado por id")
    public void deveLancarExcecaoQuandoIdNaoExistir() {

        assertThrows(MedicoNotFoundException.class, () -> {
            medicoService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Busca de médicos cadastrados")
    public void deveListarTodosOsMedicos() {

        MedicoRequestDTO medico1 = new MedicoRequestDTO(
                "Dr João",
                "12345678900",
                "Cardiologia",
                null,
                null,
                "CRM12345",
                null
        );

        MedicoRequestDTO medico2 = new MedicoRequestDTO(
                "Dr Pedro",
                "99999999999",
                "Ortopedia",
                null,
                null,
                "CRM67890",
                null
        );

        medicoService.salvar(medico1);
        medicoService.salvar(medico2);

        List<Medico> lista = medicoService.listar();

        assertNotNull(lista);
        assertEquals(2, lista.size());
    }
}