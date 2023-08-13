package com.api.management.service;

import com.api.management.dto.PessoaDto;
import com.api.management.dto.PessoaHorasGastasDto;
import com.api.management.dto.PessoaNomePeriodoHorasDto;
import com.api.management.form.PessoaForm;
import com.api.management.form.PessoaNomePeriodoForm;
import com.api.management.model.Pessoa;
import com.api.management.model.Tarefa;
import com.api.management.repository.PessoaRepository;
import com.api.management.repository.TarefaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PessoaDto save(PessoaForm pessoaForm) {
        Pessoa pessoa = modelMapper.map(pessoaForm, Pessoa.class);
        pessoaRepository.save(pessoa);

        PessoaDto pessoaDto = modelMapper.map(pessoa, PessoaDto.class);
        return pessoaDto;
    }

    public PessoaDto update(Long id, PessoaForm pessoaForm) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);

        if (pessoa != null) {
            pessoa.setNome(pessoaForm.getNome());
            pessoa.setDepartamento(pessoaForm.getDepartamento());

            pessoaRepository.save(pessoa);

            PessoaDto pessoaDto = modelMapper.map(pessoa, PessoaDto.class);

            return pessoaDto;
        }

        throw new IllegalArgumentException("Não foi possível alterar o registro desta pessoa, pois 'ID' informado é inválido!");
    }

    public String delete(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);

        if (pessoa != null) {

            for (Tarefa tarefa : pessoa.getTarefaList()) {
                tarefa.setPessoaAlocada(null);
                tarefaRepository.save(tarefa);
            }

            String nome = pessoa.getNome();

            pessoaRepository.deleteById(id);
            return nome + " foi excluído dos registros!";
        }

        throw new IllegalArgumentException("Não foi possível deletar o registro desta pessoa, pois o 'ID' informado é inválido!");
    }

    public List<PessoaHorasGastasDto> getAllPessoasDepartamentoHorasGastas() {
        List<Pessoa> pessoaList = pessoaRepository.findAll();

        List<PessoaHorasGastasDto> pessoaHorasGastasDtoList = pessoaList.stream().map(pessoa -> {
            int horasGastas = pessoa.getTarefaList().stream().filter(tarefa -> tarefa.isFinalizado()).mapToInt(tarefa -> tarefa.getDuracao()).sum();

            PessoaHorasGastasDto pessoaHorasGastasDto = new PessoaHorasGastasDto();
            pessoaHorasGastasDto.setPessoa(modelMapper.map(pessoa, PessoaDto.class));
            pessoaHorasGastasDto.setTotalDeHorasGastas(horasGastas);
            return pessoaHorasGastasDto;
        }).collect(Collectors.toList());

        return pessoaHorasGastasDtoList;
    }

    public List<PessoaNomePeriodoHorasDto> findAllPessoaPeriodoHorasGastas(PessoaNomePeriodoForm pessoaNomePeriodoForm) {

        List<Pessoa> pessoaList = pessoaRepository.findAllByNome(pessoaNomePeriodoForm.getNome());

        if (pessoaList.isEmpty()) {
            throw new IllegalArgumentException("Não existem pessoas registradas com o 'nome' informado!");
        }

        List<PessoaNomePeriodoHorasDto> pessoaNomePeriodoHorasDtoList = pessoaList.stream()
                .map(pessoa -> {
                    List<Tarefa> tarefaList = pessoa.getTarefaList().stream()
                            .filter(tarefa -> tarefa.getPrazo()
                                    .isAfter(pessoaNomePeriodoForm.getDataInicio()) && tarefa.getPrazo()
                                    .isBefore(pessoaNomePeriodoForm.getDataFim()))
                            .collect(Collectors.toList());

                    int horasGastas = tarefaList.stream().mapToInt(tarefa -> tarefa.getDuracao()).sum();

                    PessoaNomePeriodoHorasDto pessoaNomePeriodoHorasDto = new PessoaNomePeriodoHorasDto();
                    pessoaNomePeriodoHorasDto.setId(pessoa.getId());
                    pessoaNomePeriodoHorasDto.setNome(pessoa.getNome());
                    pessoaNomePeriodoHorasDto.setDataInicio(pessoaNomePeriodoForm.getDataInicio());
                    pessoaNomePeriodoHorasDto.setDataFim(pessoaNomePeriodoForm.getDataFim());
                    pessoaNomePeriodoHorasDto.setTotalHorasGastas(horasGastas);

                    return pessoaNomePeriodoHorasDto;
                })
                .collect(Collectors.toList());

        return pessoaNomePeriodoHorasDtoList;
    }

}
