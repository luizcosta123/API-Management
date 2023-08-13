package com.api.management.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaNomePeriodoForm {

    @NotBlank(message = "O campo 'nome' não pode estar vazio!")
    private String nome;

    @NotNull(message = "O campo 'dataInicio' não pode estar vazio!")
    private LocalDate dataInicio;

    @NotNull(message = "O campo 'dataFim' não pode estar vazio!")
    private LocalDate dataFim;

}
