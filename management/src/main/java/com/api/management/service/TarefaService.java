package com.api.management.service;

import com.api.management.dto.TarefaDto;
import com.api.management.form.TarefaForm;
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
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public TarefaDto save(TarefaForm tarefaForm) {
        Tarefa tarefa = modelMapper.map(tarefaForm, Tarefa.class);

        if (tarefaForm.getPessoaAlocada() != null) {
            Pessoa pessoaAlocada = pessoaRepository.findById(tarefaForm.getPessoaAlocada()).orElse(null);

            if (pessoaAlocada != null) {
                tarefa.setPessoaAlocada(pessoaAlocada);
            } else {
                throw new IllegalArgumentException("Não existem pessoas registradas com o 'ID' informado, por favor cadastre a" +
                                                   "tarefa novamente com um ID de pessoa válido!");
            }
        }

        tarefaRepository.save(tarefa);

        TarefaDto tarefaDto = modelMapper.map(tarefa, TarefaDto.class);
        return tarefaDto;
    }

    public TarefaDto alocarPessoa(Long tarefaId, Long pessoaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElse(null);
        Pessoa pessoaAlocada = pessoaRepository.findById(pessoaId).orElse(null);

        if(pessoaAlocada == null) {
            throw new IllegalArgumentException("Não foi possível encontrar o registro desta pessoa pelo 'ID' informado!");
        } else if(tarefa == null) {
            throw new IllegalArgumentException("Não foi possível encontrar o registro desta tarefa pelo 'ID' informado!");
        }

        if(pessoaAlocada.getDepartamento() == tarefa.getDepartamento()) {
                tarefa.setPessoaAlocada(pessoaAlocada);
                tarefaRepository.save(tarefa);
                TarefaDto tarefaDto = modelMapper.map(tarefa, TarefaDto.class);
                return tarefaDto;
        }

        throw new IllegalArgumentException("Não é possível alocar uma pessoa de um departamento em uma tarefa de departamento diferente!");
    }

    public TarefaDto finalizarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElse(null);

        if(tarefa == null) {
            throw new IllegalArgumentException("Não foi possível encontrar o registro desta tarefa pelo 'ID' informado!");
        } else if(tarefa.isFinalizado()) {
            throw new IllegalArgumentException("Está tarefa já está finalizada!");
        }

        tarefa.setFinalizado(true);

        tarefaRepository.save(tarefa);

        TarefaDto tarefaDto = modelMapper.map(tarefa, TarefaDto.class);
        return tarefaDto;
    }

    public List<TarefaDto> getTresTarefasMaisAntigas() {
        List<Tarefa> tarefaList = tarefaRepository.findTresTarefasSemPessoaAlocacadaMaisAntigas();

        List<TarefaDto> tarefaDtoList = tarefaList.stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaDto.class))
                .limit(3)
                .collect(Collectors.toList());

        return tarefaDtoList;
    }
}
