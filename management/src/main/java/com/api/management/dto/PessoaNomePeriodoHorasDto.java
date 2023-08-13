package com.api.management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaNomePeriodoHorasDto {

    private Long id;

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private int totalHorasGastas;

}
