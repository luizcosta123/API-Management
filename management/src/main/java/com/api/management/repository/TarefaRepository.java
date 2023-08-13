package com.api.management.repository;

import com.api.management.enuns.Departamento;
import com.api.management.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.pessoaAlocada IS NULL ORDER BY t.prazo ASC")
    List<Tarefa> findTresTarefasSemPessoaAlocacadaMaisAntigas();

    List<Tarefa> findByDepartamento(Departamento departamento);

}
