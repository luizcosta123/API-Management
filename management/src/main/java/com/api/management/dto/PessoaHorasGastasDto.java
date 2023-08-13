package com.api.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaHorasGastasDto {

    private PessoaDto pessoa;

    private Integer totalDeHorasGastas;

}
