package com.api.management.model;

import com.api.management.enuns.Departamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Enumerated(EnumType.STRING)
    private Departamento departamento;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoaAlocada", cascade = CascadeType.ALL)
    private List<Tarefa> listaDeTarefas = new ArrayList<>();

}
