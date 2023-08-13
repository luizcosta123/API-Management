package com.api.management.enuns;

public enum Departamento {

    FINANCEIRO(1L, "Financeiro"),
    COMERCIAL(2L, "Comercial"),
    DESENVOLVIMENTO(3L, "Desenvolvimento");

    private final Long id;
    private final String nome;

    Departamento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
