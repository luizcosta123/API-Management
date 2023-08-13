package com.api.management.controller;

import com.api.management.dto.TarefaDto;
import com.api.management.form.TarefaForm;
import com.api.management.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    /**
     * Cria uma nova tarefa.
     *
     * @param tarefaForm Os dados da tarefa a serem criados.
     * @return Um ResponseEntity contendo um objeto TarefaDto com os dados da tarefa criada e status HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<TarefaDto> save(@RequestBody @Valid TarefaForm tarefaForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.save(tarefaForm));
    }

    /**
     * Aloca uma pessoa em uma tarefa.
     *
     * @param tarefaId O ID da tarefa na qual a pessoa será alocada.
     * @param pessoaId O ID da pessoa a ser alocada na tarefa.
     * @return Um ResponseEntity contendo um objeto TarefaDto com os dados atualizados da tarefa e status HTTP 200 (OK).
     */
    @PutMapping("/alocar/{tarefaId}/{pessoaId}")
    public ResponseEntity<TarefaDto> alocarPessoa(@PathVariable Long tarefaId, @PathVariable Long pessoaId) {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.alocarPessoa(tarefaId, pessoaId));
    }

    /**
     * Finaliza uma tarefa.
     *
     * @param id O ID da tarefa a ser finalizada.
     * @return Um ResponseEntity contendo um objeto TarefaDto com os dados atualizados da tarefa e status HTTP 200 (OK).
     */
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TarefaDto> finalizarTarefa(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.finalizarTarefa(id));
    }

    /**
     * Obtém a lista das três tarefas com prazos mais antigos.
     *
     * @return Um ResponseEntity contendo uma lista de objetos TarefaDto com os dados das tarefas e status HTTP 200 (OK).
     */
    @GetMapping("/pendentes")
    public ResponseEntity<List<TarefaDto>> getTresTarefasMaisAntigas() {
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.getTresTarefasMaisAntigas());
    }

}
