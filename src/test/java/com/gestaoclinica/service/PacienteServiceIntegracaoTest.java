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
    @DisplayName("Salvamento de paciente com sucesso")
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
    @DisplayName("Validação de paciente sem nome")
    public void deveLancarExcecaoQuandoPacienteNaoTiverNome(){
        Paciente paciente = new Paciente();
        paciente.setCpf("1234567899");

        Exception exception = assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);});

        assertEquals("O campo 'nome' é obrigatório", exception.getMessage());

    }
    @Test
    @DisplayName("Validação de CPF vazio")
    public void deveLancarExcecaoQuandoCpfForVazio() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf("");

        assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);
        });

    }
    @Test
    @DisplayName("Validação de CPF nulo")
    public void deveLancarExcecaoQuandoCpfForNulo() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf(null);

        assertThrows(PacienteCampoObrigatorioException.class, () -> {
            pacienteService.salvar(paciente);
        });

    }
    @Test
    @DisplayName("Validação de CPF duplicado")
    public void deveLancarExcecaoQuandoCpfJaExistir() {

        Paciente paciente1 = new Paciente();
        paciente1.setNome("Maira");
        paciente1.setCpf("12345678900");

        pacienteService.salvar(paciente1);

        Paciente paciente2 = new Paciente();
        paciente2.setNome("João");
        paciente2.setCpf("12345678901");

        assertThrows(PacienteCpfJaCadastradoException.class, () -> {
            pacienteService.salvar(paciente2);});

    }
    @Test
    @DisplayName("Paciente não encontrado por id")
    public void deveLancarExcecaoQuandoIdNaoExistir() {

        assertThrows(PacienteNotFoundException.class, () -> {
            pacienteService.buscarPorId(999L);});
            
    }
    @Test
    @DisplayName("Exclusão de paciente por id")
    void deveExcluirPacienteQuandoIdExistir() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf("12345678900");

        Paciente salvo = pacienteService.salvar(paciente);

        assertNotNull(salvo);
        assertNotNull(salvo.getIdPaciente());

        Long id = salvo.getIdPaciente();

        pacienteService.excluirPorId(id);

        assertFalse(pacienteRepository.existsById(id));
    }
    @Test
    @DisplayName("Busca de pacientes cadastrados")
    void deveListarTodosOsPacientes() {

        // Arrange (organizar)
        Paciente paciente1 = new Paciente();
        paciente1.setNome("Maira");
        paciente1.setCpf("12345678900");

        Paciente paciente2 = new Paciente();
        paciente2.setNome("João");
        paciente2.setCpf("12345678901");

        pacienteService.salvar(paciente1);
        pacienteService.salvar(paciente2);

        // Act (executar)
        List<Paciente> lista = pacienteService.listar();

        // Assert (validar)
        assertNotNull(lista);
        assertEquals(2, lista.size());
    }
    }