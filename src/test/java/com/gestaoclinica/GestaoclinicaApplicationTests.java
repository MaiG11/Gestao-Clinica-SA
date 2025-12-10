// Diz que a classe faz parte do pacote com.gestaoclinica
package com.gestaoclinica;

// Importa a classe Consulta (o modelo que representa uma consulta médica)
import com.gestaoclinica.model.Consulta;
// Importa a classe Medico
import com.gestaoclinica.model.Medico;
// Importa a classe Paciente
import com.gestaoclinica.model.Paciente;
// Importa o repositório da consulta (onde salvamos, buscamos e excluímos consultas)
import com.gestaoclinica.repository.ConsultaRepository;
// Importa o serviço que tem as regras de negócio da consulta
import com.gestaoclinica.service.ConsultaService;

// Importa a anotação que marca um método como teste
import org.junit.jupiter.api.Test;
// Permite usar o Mockito junto com o JUnit
import org.junit.jupiter.api.extension.ExtendWith;
// Cria objetos falsos automaticamente (mocks)
import org.mockito.InjectMocks;
// Anotação que cria objetos falsos (mockar = simular)
import org.mockito.Mock;
// Ativa o Mockito nos testes
import org.mockito.junit.jupiter.MockitoExtension;

// Importa Optional para simular retorno do banco (pode ter valor ou não)
import java.util.Optional;

// Importa métodos de teste como assertEquals, assertThrows, assertNotNull
import static org.junit.jupiter.api.Assertions.*;
// Importa comandos do Mockito: when, verify, etc.
import static org.mockito.Mockito.*;

// Diz que esta classe vai usar as funcionalidades do Mockito
@ExtendWith(MockitoExtension.class)
class GestaoClinicaApplicationTests {

    // Cria um repositório falso para simular o banco de dados
    @Mock
    private ConsultaRepository repository;

    // Cria o service e coloca automaticamente o repositório falso dentro dele
    @InjectMocks
    private ConsultaService service;


    //     TESTE 1 – SALVAR CONSULTA COM DADOS VÁLIDOS
    
    @Test
    void deveSalvarConsultaComDadosValidos() {

       
        Paciente paciente = new Paciente();//é um objeto da classe Paciente que está sendo criado.
        Medico medico = new Medico(); //é um objeto da classe Medico que está sendo criado.
        Consulta consulta = new Consulta();// é um objeto da classe Consulta que está sendo criado.

        consulta.setPaciente(paciente);//é um método da classe Consulta que define o paciente associado a essa consulta.
        consulta.setMedico(medico);//é um método da classe Consulta que define o médico associado a essa consulta.
        consulta.setRetornoNecessario(false);//é um método da classe Consulta que define se a consulta requer um retorno ou não.
        // retorna um boolean (true ou false). Nesse caso, estamos dizendo que não é necessário retorno.
        
        // Aqui simulei o comportamento do repositório
        // Diz: "Quando salvar a consulta, devolva a própria consulta"
        when(repository.save(consulta)).thenReturn(consulta);//thenReturn(consulta) = "devolva essa consulta como resposta"

        // Chamamos o método do service que salva a consulta
        Consulta resultado = service.salvar(consulta);

        assertNotNull(resultado); //é um método do JUnit usado nos testes para verificar se um valor NÃO é nulo.

        // Verifica se o método save() do repositório foi chamado
        verify(repository).save(consulta); //é um comando usado quando você está fazendo testes com Mockito (uma biblioteca de testes do Java).
    }

   
    //     TESTE 2 – ERRO AO SALVAR SEM PACIENTE
    
    @Test
    void deveDarErroQuandoPacienteNaoExiste() {

        Medico medico = new Medico();//é um objeto da classe Medico que está sendo criado.
        Consulta consulta = new Consulta();// é um objeto da classe Consulta que está sendo criado sem definir um paciente.
        consulta.setMedico(medico); //criando um novo objeto da classe Medico e armazenando ele para poder usar depois.
        consulta.setRetornoNecessario(false);//é um método da classe Consulta que define se a consulta requer um retorno ou não.

        // Aqui esperamos que o método salvar gere um erro
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {//assertThrows é um comando de teste(testa se realmente aconteceu um erro do tipo esperado).
        //RuntimeException é o nome da classe que representa esse tipo de erro.
        //Aqui estou criando uma variável chamada erro.
        //Essa variável vai guardar o erro que realmente acontecer.
        //Tipo dela: RuntimeException → ou seja, esperamos que esse tipo de erro aconteça.

            service.salvar(consulta);//é o método do serviço que estamos testando, que tenta salvar a consulta que criei sem paciente.
        });

        // Verifica se a mensagem do erro é exatamente a esperada
        assertEquals("Paciente é obrigatório", erro.getMessage());//assertEquals é um método usado em testes para comparar dois valores.
        //Verifique que um valor é igual ao outro.
    }

    
    //     TESTE 3 – BUSCAR CONSULTA POR ID EXISTENTE

    @Test
    void deveBuscarConsultaPorIdExistente() {

        
        Consulta consulta = new Consulta();// é um objeto da classe Consulta que está sendo criado.

        // Quando o repositório procurar ID 1, devolve essa consulta
        when(repository.findById(1L)).thenReturn(Optional.of(consulta));//when é um comando usado em testes com Mockito para simular o comportamento de métodos.
        //findById(1L) = "Quando o método findById for chamado com o valor 1L (ID 1)"
        //thenReturn(Optional.of(consulta)) = "devolva um Optional que contém a consulta que criamos".
        //Optional.of(consulta) = "um contêiner que pode conter um valor (nesse caso, a consulta) ou estar vazio".
        //Usamos Optional para evitar problemas com valores nulos.
        //L = Long (tipo de dado usado para IDs grandes).
        //1L = o número 1 como um valor do tipo Long.
        //Então, quando chamamos findById(1L), ele vai devolver a consulta que criamos.
        //Isso simula o comportamento do banco de dados.
        
        // Chama o método do service que busca a consulta
        Consulta resultado = service.buscarPorId(1L);

        // Verifica se encontrou alguma coisa
        assertNotNull(resultado); // esse método serve para verificar se o resultado não é nulo.

        // Verifica se o repositório realmente chamou findById(1)
        verify(repository).findById(1L); //verify é um comando usado em testes com Mockito para verificar se um método específico foi chamado com os parâmetros esperados.
    }

    //     TESTE 4 – ERRO AO BUSCAR CONSULTA INEXISTENTE
  
    @Test
    void deveDarErroQuandoConsultaNaoExiste() {

        // Simula que não existe consulta com o ID 2
        when(repository.findById(2L)).thenReturn(Optional.empty());//when é um comando usado em testes com Mockito para simular o comportamento de métodos.
        //findById(2L) = "Quando o método findById for chamado com o valor 2L (ID 2)".
        //thenReturn(Optional.empty()) = "devolva um Optional vazio, ou seja, sem nenhum valor dentro".
        //Optional.empty() = "um contêiner que não contém nenhum valor".
        

        // Espera que buscarPorId gere erro
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {//assertThrows é um comando de teste que verifica se uma exceção específica é lançada durante a execução de um bloco de código.
            service.buscarPorId(2L);
        });
        // Chama o método do service que busca a consulta
        

        // Verifica se a mensagem do erro está correta
        assertEquals("Consulta não encontrada", erro.getMessage());//assertEquals é um método usado em testes para verificar se dois valores são iguais.
    }//Verifique se a mensagem do erro é igual a ‘Consulta não encontrada.
    //É a mensagem que o service deve enviar quando alguém tenta buscar uma consulta que não existe.
   
    //     TESTE 5 – EXCLUIR CONSULTA POR ID
  
    @Test
    void deveExcluirConsultaPorId() {

        // Chama o método que exclui a consulta
        service.excluirPorId(3L);//é um método do serviço que estou testando, que tenta excluir a consulta com o ID 3.

        // Verifica se o repositório chamou realmente o deleteById
        verify(repository).deleteById(3L);//verify é um comando usado em testes com Mockito para verificar se um método específico foi chamado com os parâmetros esperados.
    }
}
