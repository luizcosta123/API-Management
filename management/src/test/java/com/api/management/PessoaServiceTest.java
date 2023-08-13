package com.api.management;

import com.api.management.dto.PessoaDto;
import com.api.management.dto.PessoaHorasGastasDto;
import com.api.management.dto.PessoaNomePeriodoHorasDto;
import com.api.management.enuns.Departamento;
import com.api.management.form.PessoaForm;
import com.api.management.form.PessoaNomePeriodoForm;
import com.api.management.model.Pessoa;
import com.api.management.model.Tarefa;
import com.api.management.repository.PessoaRepository;
import com.api.management.service.PessoaService;
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

public class PessoaServiceTest {

    private static final Long ID = 1L;
    private static final String nome = "Luiz";
    private static final Departamento departamento = Departamento.DESENVOLVIMENTO;

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ModelMapper modelMapper;

    private Pessoa pessoa;

    private PessoaDto pessoaDto;

    private PessoaHorasGastasDto pessoaHorasGastasDto;

    private PessoaNomePeriodoHorasDto pessoaNomePeriodoHorasDto;

    private PessoaForm pessoaForm;

    private PessoaNomePeriodoForm pessoaNomePeriodoForm;

    private Tarefa tarefa1;
    private Tarefa tarefa2;

    private List<Pessoa> pessoaList = new ArrayList<>();

    private List<PessoaHorasGastasDto> pessoaHorasGastasDtoList = new ArrayList<>();

    private List<PessoaNomePeriodoHorasDto> pessoaNomePeriodoHorasDtoList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoa();
        startTarefa();
    }

    @Test
    void whenSaveThenReturnAnPessoaDto() {
        when(pessoaRepository.save(any())).thenReturn(pessoa);
        when(modelMapper.map(any(PessoaForm.class), eq(Pessoa.class))).thenReturn(pessoa);
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDto.class))).thenReturn(pessoaDto);

        PessoaDto pessoaDtoTeste = pessoaService.save(pessoaForm);

        assertEquals(PessoaDto.class, pessoaDtoTeste.getClass());
        assertEquals(pessoaDto.getNome(), pessoaDtoTeste.getNome());
        assertEquals(pessoaDto.getDepartamento(), pessoaDtoTeste.getDepartamento());
    }

    @Test
    void update() {
        Pessoa pessoaAlterada = new Pessoa();
        pessoaAlterada.setId(ID);
        pessoaAlterada.setNome("Afonso");
        pessoaAlterada.setDepartamento(Departamento.FINANCEIRO);
        pessoaAlterada.setTarefaList(new ArrayList<>());

        PessoaForm pessoaFormAlterada = new PessoaForm();
        pessoaFormAlterada.setNome("Afonso");
        pessoaFormAlterada.setDepartamento(Departamento.FINANCEIRO);

        PessoaDto pessoaDtoAlterada = new PessoaDto();
        pessoaDtoAlterada.setId(1L);
        pessoaDtoAlterada.setNome("Afonso");
        pessoaFormAlterada.setDepartamento(Departamento.FINANCEIRO);

        when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoaAlterada));
        when(pessoaRepository.save(any())).thenReturn(pessoaAlterada);
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDto.class))).thenReturn(pessoaDtoAlterada);

        PessoaDto pessoaDtoTeste = pessoaService.update(ID, pessoaFormAlterada);

        assertEquals(PessoaDto.class, pessoaDtoTeste.getClass());
        assertEquals(pessoaDtoAlterada.getNome(), pessoaDtoTeste.getNome());
        assertEquals(pessoaDtoAlterada.getDepartamento(), pessoaDtoTeste.getDepartamento());
    }

    @Test
    void whenDeleteReturnAnSuccessMessage() {
        when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa));

        String successMessage = pessoaService.delete(ID);
        assertEquals(nome + " foi excluído dos registros!", successMessage);
    }

    @Test
    void whenGetAllPessoasDepartamentoHorasGastasReturnAnPessoaHorasGastasDto() {
        when(pessoaRepository.findAll()).thenReturn(pessoaList);
        when(modelMapper.map(any(Pessoa.class), eq(PessoaDto.class))).thenReturn(pessoaDto);

        pessoa.getTarefaList().add(tarefa1);
        pessoa.getTarefaList().add(tarefa2);

        List<PessoaHorasGastasDto> pessoaHorasGastasDtoListTeste = pessoaService.getAllPessoasDepartamentoHorasGastas();

        assertEquals(this.pessoaHorasGastasDtoList.getClass(), pessoaHorasGastasDtoListTeste.getClass());
        assertEquals(10, pessoaHorasGastasDtoListTeste.get(0).getTotalDeHorasGastas());
        assertEquals(nome, pessoaHorasGastasDtoListTeste.get(0).getPessoa().getNome());
        assertEquals(departamento, pessoaHorasGastasDtoListTeste.get(0).getPessoa().getDepartamento());
        assertEquals(1, pessoaHorasGastasDtoListTeste.size());
    }

    @Test
    void whenGetAllPessoaPeriodoHorasGastasReturnAnPessoaNomePeriodoHorasDto() {
        when(pessoaRepository.findAllByNome(pessoaForm.getNome())).thenReturn(pessoaList);

        pessoa.getTarefaList().add(tarefa1);
        pessoa.getTarefaList().add(tarefa2);

        List<PessoaNomePeriodoHorasDto> pessoaNomePeriodoHorasDtoListTeste = pessoaService.findAllPessoaPeriodoHorasGastas(pessoaNomePeriodoForm);

        assertEquals(pessoaNomePeriodoHorasDtoList.getClass(), pessoaNomePeriodoHorasDtoListTeste.getClass());
        assertEquals(pessoaNomePeriodoHorasDtoList.get(0).getNome(), pessoaNomePeriodoHorasDtoListTeste.get(0).getNome());
        assertEquals(pessoaNomePeriodoHorasDtoList.get(0).getTotalHorasGastas(), pessoaNomePeriodoHorasDtoListTeste.get(0).getTotalHorasGastas());
        assertEquals(pessoaNomePeriodoHorasDtoList.get(0).getDataInicio(), pessoaNomePeriodoHorasDtoListTeste.get(0).getDataInicio());
        assertEquals(pessoaNomePeriodoHorasDtoList.get(0).getDataFim(), pessoaNomePeriodoHorasDtoListTeste.get(0).getDataFim());
    }

    private void startPessoa() {
        pessoa = new Pessoa();
        pessoa.setId(ID);
        pessoa.setNome(nome);
        pessoa.setDepartamento(Departamento.DESENVOLVIMENTO);
        pessoa.setTarefaList(new ArrayList<>());

        pessoaDto = new PessoaDto();
        pessoaDto.setId(ID);
        pessoaDto.setNome(nome);
        pessoaDto.setDepartamento(Departamento.DESENVOLVIMENTO);

        pessoaForm = new PessoaForm();
        pessoaForm.setNome(nome);
        pessoaForm.setDepartamento(Departamento.DESENVOLVIMENTO);

        pessoaHorasGastasDto = new PessoaHorasGastasDto();
        pessoaHorasGastasDto.setPessoa(pessoaDto);
        pessoaHorasGastasDto.setTotalDeHorasGastas(10);

        pessoaNomePeriodoForm = new PessoaNomePeriodoForm();
        pessoaNomePeriodoForm.setNome(pessoa.getNome());
        pessoaNomePeriodoForm.setDataInicio(LocalDate.now().plusDays(10));
        pessoaNomePeriodoForm.setDataFim(LocalDate.now().plusDays(30));

        pessoaNomePeriodoHorasDto = new PessoaNomePeriodoHorasDto();
        pessoaNomePeriodoHorasDto.setId(1l);
        pessoaNomePeriodoHorasDto.setNome(pessoa.getNome());
        pessoaNomePeriodoHorasDto.setTotalHorasGastas(10);
        pessoaNomePeriodoHorasDto.setDataInicio(LocalDate.now().plusDays(10));
        pessoaNomePeriodoHorasDto.setDataFim(LocalDate.now().plusDays(30));

        pessoaList.add(pessoa);

        pessoaHorasGastasDtoList.add(pessoaHorasGastasDto);

        pessoaNomePeriodoHorasDtoList.add(pessoaNomePeriodoHorasDto);
    }

    private void startTarefa() {
        tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        tarefa1.setTitulo("Tarefa - 1");
        tarefa1.setDescricao("Descrição da tarefa 1");
        tarefa1.setPrazo(LocalDate.now().plusDays(20));
        tarefa1.setDepartamento(Departamento.DESENVOLVIMENTO);
        tarefa1.setFinalizado(true);
        tarefa1.setDuracao(5);

        tarefa2 = new Tarefa();
        tarefa2.setId(1L);
        tarefa2.setTitulo("Tarefa - 2");
        tarefa2.setDescricao("Descrição da tarefa 2");
        tarefa2.setPrazo(LocalDate.now().plusDays(20));
        tarefa2.setDepartamento(Departamento.DESENVOLVIMENTO);
        tarefa2.setFinalizado(true);
        tarefa2.setDuracao(5);
    }

}
