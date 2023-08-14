package com.api.management;

import com.api.management.dto.TarefaDto;
import com.api.management.enuns.Departamento;
import com.api.management.form.TarefaForm;
import com.api.management.model.Pessoa;
import com.api.management.model.Tarefa;
import com.api.management.repository.PessoaRepository;
import com.api.management.repository.TarefaRepository;
import com.api.management.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TarefaServiceTest {

    private static final Long ID = 1L;
    private static final String nome = "Luiz";
    private static final Departamento departamento = Departamento.DESENVOLVIMENTO;

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ModelMapper modelMapper;

    private Tarefa tarefa1;

    private Tarefa tarefa2;

    private Tarefa tarefa3;

    private Pessoa pessoa;

    private TarefaDto tarefaDto;

    private TarefaForm tarefaForm;

    private List<Tarefa> tarefaList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTarefa();
        startPessoa();
    }

    @Test
    void whenSaveThenReturnAnTarefaDto() {
        when(tarefaRepository.save(any())).thenReturn(tarefa1);
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(any(TarefaForm.class), eq(Tarefa.class))).thenReturn(tarefa1);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDto.class))).thenReturn(tarefaDto);

        TarefaDto tarefaDtoTeste = tarefaService.save(tarefaForm);

        assertEquals(TarefaDto.class, tarefaDtoTeste.getClass());
        assertEquals(tarefaDto.getTitulo(), tarefaDtoTeste.getTitulo());
        assertEquals(tarefaDto.getDescricao(), tarefaDtoTeste.getDescricao());
        assertEquals(tarefaDto.getDepartamento(), tarefaDtoTeste.getDepartamento());
        assertEquals(tarefaDto.getPrazo(), tarefaDtoTeste.getPrazo());
        assertEquals(tarefaDto.getDuracao(), tarefaDtoTeste.getDuracao());
        assertEquals(tarefaDto.getPessoaAlocada(), tarefaDtoTeste.getPessoaAlocada());
        assertEquals(tarefaDto.isFinalizado(), tarefaDtoTeste.isFinalizado());
    }

    @Test
    void whenAlocarPessoaParaTarefaEspecificaReturnAnTarefaDto() {
        when(tarefaRepository.save(any())).thenReturn(tarefa1);
        when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefa1));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDto.class))).thenReturn(tarefaDto);

        TarefaDto tarefaDtoTeste = tarefaService.alocarPessoaParaTarefaEspecifica(tarefa1.getId(), pessoa.getId());

        assertEquals(TarefaDto.class, tarefaDtoTeste.getClass());
        assertEquals(tarefaDto.getTitulo(), tarefaDtoTeste.getTitulo());
        assertEquals(tarefaDto.getDescricao(), tarefaDtoTeste.getDescricao());
        assertEquals(tarefaDto.getDepartamento(), tarefaDtoTeste.getDepartamento());
        assertEquals(tarefaDto.getPrazo(), tarefaDtoTeste.getPrazo());
        assertEquals(tarefaDto.getDuracao(), tarefaDtoTeste.getDuracao());
        assertEquals(tarefaDto.getPessoaAlocada(), tarefaDtoTeste.getPessoaAlocada());
        assertEquals(tarefaDto.isFinalizado(), tarefaDtoTeste.isFinalizado());
    }

    @Test
    void whenFinalizarTarefaReturnAnTarefaDtoModified() {
        Tarefa tarefaParaSerFinalizada = new Tarefa();
        tarefaParaSerFinalizada.setId(1L);
        tarefaParaSerFinalizada.setTitulo("Tarefa - 1");
        tarefaParaSerFinalizada.setDescricao("Descrição da Descrição da tarefa 1");
        tarefaParaSerFinalizada.setPrazo(LocalDate.now().plusDays(200));
        tarefaParaSerFinalizada.setDepartamento(departamento);
        tarefaParaSerFinalizada.setFinalizado(false);
        tarefaParaSerFinalizada.setDuracao(5);

        TarefaDto tarefaDtoFinalizada = new TarefaDto();
        tarefaDtoFinalizada.setId(1L);
        tarefaDtoFinalizada.setTitulo("Tarefa - 1");
        tarefaDtoFinalizada.setDescricao("Descrição da Descrição da tarefa 1");
        tarefaDtoFinalizada.setPrazo(LocalDate.now().plusDays(200));
        tarefaDtoFinalizada.setDepartamento(departamento);
        tarefaDtoFinalizada.setFinalizado(true);
        tarefaDtoFinalizada.setDuracao(5);

        when(tarefaRepository.save(any())).thenReturn(tarefa1);
        when(tarefaRepository.findById(tarefa1.getId())).thenReturn(Optional.of(tarefaParaSerFinalizada));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDto.class))).thenReturn(tarefaDtoFinalizada);

        TarefaDto tarefaDtoTeste = tarefaService.finalizarTarefa(tarefa1.getId());

        assertEquals(true, tarefaDtoTeste.isFinalizado());
    }

    @Test
    void whenGetTresTarefasMaisAntigasReturnAnTarefaDtoListOfLength3() {
        when(tarefaRepository.findTresTarefasSemPessoaAlocacadaMaisAntigas()).thenReturn(tarefaList);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDto.class))).thenReturn(tarefaDto);

        List<TarefaDto> tarefaDtoListTeste = tarefaService.getTresTarefasMaisAntigas();

        assertEquals(3, tarefaDtoListTeste.size());
    }

    private void startTarefa() {
        tarefa1 = new Tarefa();
        tarefa1.setId(ID);
        tarefa1.setTitulo("Tarefa - 1");
        tarefa1.setDescricao("Descrição da tarefa 1");
        tarefa1.setPrazo(LocalDate.now().plusDays(200));
        tarefa1.setDepartamento(departamento);
        tarefa1.setFinalizado(false);
        tarefa1.setDuracao(5);

        tarefa2 = new Tarefa();
        tarefa2.setId(2l);
        tarefa2.setTitulo("Tarefa - 2");
        tarefa2.setDescricao("Descrição da tarefa 2");
        tarefa2.setPrazo(LocalDate.now().plusDays(200));
        tarefa2.setDepartamento(departamento);
        tarefa2.setFinalizado(false);
        tarefa2.setDuracao(5);

        tarefa3 = new Tarefa();
        tarefa3.setId(3l);
        tarefa3.setTitulo("Tarefa - 3");
        tarefa3.setDescricao("Descrição da tarefa 3");
        tarefa3.setPrazo(LocalDate.now().plusDays(200));
        tarefa3.setDepartamento(departamento);
        tarefa3.setFinalizado(false);
        tarefa3.setDuracao(5);

        tarefaForm = new TarefaForm();
        tarefaForm.setTitulo("Tarefa - 1");
        tarefaForm.setDescricao("Descrição da tarefa 1");
        tarefaForm.setPrazo(LocalDate.now().plusDays(200));
        tarefaForm.setDepartamento(departamento);
        tarefaForm.setFinalizado(false);
        tarefaForm.setPessoaAlocada(ID);
        tarefaForm.setDuracao(5);

        tarefaDto = new TarefaDto();
        tarefaDto.setId(ID);
        tarefaDto.setTitulo("Tarefa - 1");
        tarefaDto.setDescricao("Descrição da tarefa 1");
        tarefaDto.setPrazo(LocalDate.now().plusDays(200));
        tarefaDto.setDepartamento(departamento);
        tarefaDto.setFinalizado(false);
        tarefaDto.setDuracao(5);

        tarefaList.add(tarefa1);
        tarefaList.add(tarefa2);
        tarefaList.add(tarefa3);
    }

    private void startPessoa() {
        pessoa = new Pessoa();
        pessoa.setId(ID);
        pessoa.setNome(nome);
        pessoa.setDepartamento(departamento);
        pessoa.setTarefaList(tarefaList);
    }

}
