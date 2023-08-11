package com.api.management.form;

import com.api.management.enuns.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaForm {

    @NotBlank(message = "O campo 'nome' não pode estar vazio!")
    private String nome;

    @NotNull(message = "O campo 'departamento' não pode estar vazio!")
    private Departamento departamento;

}
