package com.api.management.form;

import com.api.management.enuns.Departamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TarefaForm {

    @NotBlank(message = "O campo 'titulo' não pode estar vazio!")
    private String titulo;

    @NotBlank(message = "O campo 'descricao' não pode estar vazio!")
    private String descricao;

    @NotNull(message = "O campo 'prazo' não pode estar vazio!")
    private LocalDate prazo;

    @NotNull(message = "O campo 'departamento' não pode estar vazio!")
    private Departamento departamento;

    @NotNull(message = "O campo 'duracao' não pode estar vazio!")
    private Integer duracao;

    @Min(value = 1, message = "O ID de pessoaAlocada precisa ser maior que 1!")
    private Long pessoaAlocada;

    @NotNull(message = "O campo 'nome' não pode estar vazio!")
    private boolean finalizado;

}
