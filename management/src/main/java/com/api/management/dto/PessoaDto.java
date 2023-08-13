package com.api.management.dto;

import com.api.management.enuns.Departamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaDto {

    private Long id;

    private String nome;

    private Departamento departamento;

}
