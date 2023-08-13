package com.api.management.service;

import com.api.management.dto.DepartamentoDto;
import com.api.management.dto.DepartamentoPessoasTarefasDto;
import com.api.management.enuns.Departamento;
import com.api.management.model.Pessoa;
import com.api.management.model.Tarefa;
import com.api.management.repository.PessoaRepository;
import com.api.management.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<DepartamentoPessoasTarefasDto> getDepartamentoQuantidadePessoasTarefas() {
        List<DepartamentoPessoasTarefasDto> departamentoPessoasTarefasDtoList = new ArrayList<>();

        for (Departamento departamento : Departamento.values()) {
            List<Pessoa> pessoaList = pessoaRepository.findByDepartamento(departamento);
            List<Tarefa> tarefaList = tarefaRepository.findByDepartamento(departamento);

            int quantidadePessoas = pessoaList.size();
            int quantidadeTarefas = tarefaList.size();

            DepartamentoDto departamentoDto = new DepartamentoDto();
            departamentoDto.setId(departamento.getId());
            departamentoDto.setNome(departamento.getNome());

            DepartamentoPessoasTarefasDto departamentoPessoasTarefasDto = new DepartamentoPessoasTarefasDto();
            departamentoPessoasTarefasDto.setDepartamento(departamentoDto);
            departamentoPessoasTarefasDto.setQuantidadeDePessoas(quantidadePessoas);
            departamentoPessoasTarefasDto.setQuantidadeDeTarefas(quantidadeTarefas);

            departamentoPessoasTarefasDtoList.add(departamentoPessoasTarefasDto);
        }

        return departamentoPessoasTarefasDtoList;
    }

}
