package com.api.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoPessoasTarefasDto {

    private DepartamentoDto departamento;

    private Integer quantidadeDePessoas;

    private Integer quantidadeDeTarefas;

}
