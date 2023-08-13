package com.api.management.dto;

import com.api.management.enuns.Departamento;
import com.api.management.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TarefaDto {

    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate prazo;

    private Departamento departamento;

    private Integer duracao;

    private Pessoa pessoaAlocada;

    private boolean finalizado;

}
