package com.gestaoclinica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gestaoclinica.exception.exceptionPaciente.PacienteCampoObrigatorioException;
import com.gestaoclinica.exception.exceptionPaciente.PacienteCpfJaCadastradoException;
import com.gestaoclinica.exception.exceptionPaciente.PacienteNotFoundException;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.PacienteRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional

public class PacienteServiceIntegracaoTest {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteRepository pacienteRepository;

@Test
@DisplayName("Deve salvar um paciente com sucesso")
public void deveSalvarPacienteComSucesso(){
    Paciente paciente = new Paciente();
    paciente.setNome ("Wellington Braz");
    paciente.setCpf("1234567899");
    Paciente pacienteSalvo = pacienteService.salvar(paciente);

    assertNotNull(pacienteSalvo.getIdPaciente());
    assertEquals("Wellington Braz", pacienteSalvo.getNome());
    assertEquals("1234567899", pacienteSalvo.getCpf());
    assertNotNull(pacienteSalvo.getDataCadastro());

}
@Test
@DisplayName("Deve lançar exceção ao tentar salvar paciente sem nome")
public void deveLancarExcecaoQuandoPacienteNaoTiverNome(){
    Paciente paciente = new Paciente();
    paciente.setCpf("1234567899");

    Exception exception = assertThrows(PacienteCampoObrigatorioException.class, () -> {
        pacienteService.salvar(paciente);});

    assertEquals("O campo 'nome' é obrigatório", exception.getMessage());

}
@Test
@DisplayName("Deve lançar exceção quando CPF for nulo")
 public void deveLancarExcecaoQuandoCpfForNulo() {

    Paciente paciente = new Paciente();
    paciente.setNome("Maira");
    paciente.setCpf(null);

    assertThrows(PacienteCampoObrigatorioException.class, () -> {
        pacienteService.salvar(paciente);});

}
@Test
@DisplayName("Deve lançar exceção quando CPF já estiver cadastrado")
 public void deveLancarExcecaoQuandoCpfJaExistir() {

    Paciente paciente1 = new Paciente();
    paciente1.setNome("Maira");
    paciente1.setCpf("12345678900");

    pacienteService.salvar(paciente1);

    Paciente paciente2 = new Paciente();
    paciente2.setNome("João");
    paciente2.setCpf("12345678900");

    assertThrows(PacienteCpfJaCadastradoException.class, () -> {
        pacienteService.salvar(paciente2);});

}
@Test
@DisplayName("Deve lançar exceção ao buscar paciente com id inexistente")
public void deveLancarExcecaoQuandoIdNaoExistir() {

    assertThrows(PacienteNotFoundException.class, () -> {
        pacienteService.buscarPorId(999L);});
        
}
@Test
@DisplayName("Deve excluir paciente quando id existir")
void deveExcluirPacienteQuandoIdExistir() {

    Paciente paciente = new Paciente();
    paciente.setNome("Maira");
    paciente.setCpf("12345678900");

    Paciente salvo = pacienteService.salvar(paciente);

    assertNotNull(salvo);
    assertNotNull(salvo.getId());

    Long id = salvo.getId();

    pacienteService.excluirPorId(id);

    assertFalse(pacienteRepository.existsById(id));
}
}