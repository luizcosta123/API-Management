package com.api.management.model;

import com.api.management.enuns.Departamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String descricao;

    @Column
    private LocalDate prazo;

    @Enumerated(EnumType.STRING)
    private Departamento departamento;

    @Column
    private Integer duracao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoaAlocada;

    @Column
    private boolean finalizado;

}
