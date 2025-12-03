package com.gestaoclinica;

// Importa a classe Consulta (modelo)
import com.gestaoclinica.model.Consulta;
// Importa a classe Médico (modelo)
import com.gestaoclinica.model.Medico;
// Importa a classe Paciente (modelo)
import com.gestaoclinica.model.Paciente;
// Importa o repositório da Consulta
import com.gestaoclinica.repository.ConsultaRepository;
// Importa o serviço ConsultaService
import com.gestaoclinica.service.ConsultaService;
// Importa a anotação de teste do JUnit
import org.junit.jupiter.api.Test;
// Permite integrar o Mockito com o JUnit
import org.junit.jupiter.api.extension.ExtendWith;
// Importa o Mockito para injeção automática de dependências
import org.mockito.InjectMocks;
// Importa a anotação que cria objetos falsos (mocks)
import org.mockito.Mock;
// Ativa o Mockito no projeto
import org.mockito.junit.jupiter.MockitoExtension;
// Importa Optional para simular retorno do banco
import java.util.Optional;
// Importa métodos de verificação do JUnit
import static org.junit.jupiter.api.Assertions.*;
// Importa os comandos do Mockito como verify e when
import static org.mockito.Mockito.*;

// Ativa o Mockito junto com o JUnit
@ExtendWith(MockitoExtension.class)
class GestaoClinicaApplicationTests {

    // Cria um "banco de dados falso"
    @Mock
    private ConsultaRepository repository;

    // Cria o ConsultaService e injeta o repositório falso nele
    @InjectMocks
    private ConsultaService service;


   
    // CENÁRIO 1 – Consulta válida
    @Test
    void deveSalvarConsultaComDadosValidos() {

        // Cria um paciente
        Paciente paciente = new Paciente();
        // Cria um médico
        Medico medico = new Medico();
        // Cria uma consulta vazia
        Consulta consulta = new Consulta();
        // Define o paciente da consulta
        consulta.setPaciente(paciente);
        // Define o médico da consulta
        consulta.setMedico(medico);

        // Define que NÃO precisa de retorno
        consulta.setRetornoNecessario(false);

        // Simula o método save do repositório
        when(repository.save(consulta)).thenReturn(consulta);

        // Chama o método salvar no service
        Consulta resultado = service.salvar(consulta);

        // Verifica se o retorno não é nulo
        assertNotNull(resultado);

        // Confere se o método save foi chamado
        verify(repository).save(consulta);
    }

    // CENÁRIO 2 – Paciente inexistente
    @Test
    void deveDarErroQuandoPacienteNaoExiste() {

        // Cria um médico
        Medico medico = new Medico();
        // Cria uma consulta
        Consulta consulta = new Consulta();

        // Define apenas o médico (paciente não é definido)
        consulta.setMedico(medico);

        // Define que não precisa de retorno
        consulta.setRetornoNecessario(false);

        // Executa o método salvar esperando erro
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            service.salvar(consulta);
        });

        // Confere se a mensagem do erro está correta
        assertEquals("Paciente é obrigatório", erro.getMessage());
    }

    // CENÁRIO 3 – Buscar por ID válido
    @Test
    void deveBuscarConsultaPorIdExistente() {

        // Cria uma consulta falsa
        Consulta consulta = new Consulta();

        // Simula o banco retornando consulta para ID 1
        when(repository.findById(1L)).thenReturn(Optional.of(consulta));

        // Busca a consulta pelo ID
        Consulta resultado = service.buscarPorId(1L);

        // Verifica se encontrou
        assertNotNull(resultado);

        // Confere se o método findById foi chamado
        verify(repository).findById(1L);
    }

    // CENÁRIO 4 – Consulta inexistente
    @Test
    void deveDarErroQuandoConsultaNaoExiste() {

        // Simula banco sem registros para ID 2
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // Executa buscando consulta que não existe
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(2L);
        });

        // Confere se a mensagem está correta
        assertEquals("Consulta não encontrada", erro.getMessage());
    }

    // CENÁRIO 5 – Cancelar consulta
    // ===================================
    @Test
    void deveExcluirConsultaPorId() {

        // Executa o método de exclusão
        service.excluirPorId(3L);

        // Confirma que o método delete foi chamado
        verify(repository).deleteById(3L);
    }
}
