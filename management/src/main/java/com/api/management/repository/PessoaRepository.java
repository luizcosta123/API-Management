package com.api.management.repository;

import com.api.management.enuns.Departamento;
import com.api.management.model.Pessoa;
import com.api.management.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findAllByNome(String nome);

    List<Pessoa> findByDepartamento(Departamento departamento);

}
