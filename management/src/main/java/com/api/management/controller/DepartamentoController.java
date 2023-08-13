package com.api.management.controller;

import com.api.management.dto.DepartamentoPessoasTarefasDto;
import com.api.management.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// - Listar departamento e quantidade de pessoas e tarefas (get/departamentos)

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    /**
     * Obtém a lista de todos os departamentos.
     *
     * @return Um ResponseEntity contendo uma lista de objetos DepartamentoPessoasTarefasDto com o
     * nome, quantidade de pessoas, quantidade de tarefas deste departamente e status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<DepartamentoPessoasTarefasDto>> getDepartamentoQuantidadePessoasETarefas() {
        return ResponseEntity.status(HttpStatus.OK).body(departamentoService.getDepartamentoQuantidadePessoasTarefas());
    }

}
