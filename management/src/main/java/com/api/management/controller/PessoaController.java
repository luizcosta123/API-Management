package com.api.management.controller;

import com.api.management.dto.PessoaDto;
import com.api.management.dto.PessoaHorasGastasDto;
import com.api.management.dto.PessoaNomePeriodoHorasDto;
import com.api.management.form.PessoaForm;
import com.api.management.form.PessoaNomePeriodoForm;
import com.api.management.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    /**
     * Cria uma nova pessoa.
     *
     * @param pessoaForm Os dados da pessoa a serem criados.
     * @return Um ResponseEntity contendo um objeto PessoaDto
     * com os dados da pessoa criada e status HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<PessoaDto> save(@RequestBody @Valid PessoaForm pessoaForm) { // ok
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoaForm));
    }

    /**
     * Atualiza os dados de uma pessoa existente.
     *
     * @param id         O ID da pessoa a ser atualizada.
     * @param pessoaForm Os novos dados da pessoa.
     * @return Um ResponseEntity contendo um objeto PessoaDto
     * com os dados atualizados da pessoa e status HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDto> update(@PathVariable Long id, @RequestBody @Valid PessoaForm pessoaForm) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.update(id, pessoaForm));
    }

    /**
     * Exclui uma pessoa.
     *
     * @param id O ID da pessoa a ser excluída.
     * @return Um ResponseEntity contendo uma mensagem de confirmação da
     * exclusão e status HTTP 200 (OK).
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.delete(id));
    }

    /**
     * Obtém a lista de todas as pessoas.
     *
     * @return Um ResponseEntity contendo uma lista de objetos PessoaHorasGastasDto com
     * os dados das pessoas, departamento, horas gastas e status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<PessoaHorasGastasDto>> getAllPessoasDepartamentoHorasGastas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAllPessoasDepartamentoHorasGastas());
    }

    /**
     * Obtém a lista de todas as pessoas com as horas gastas dentro de um determinado período.
     *
     * @param pessoaNomePeriodoForm Um objeto contendo o nome da pessoa, data de início e a data final.
     * @return Um ResponseEntity contendo uma lista de objetos PessoaNomePeriodoHorasDto
     * com os dados das pessoas, nome, período, horas gastas e status HTTP 200 (OK).
     */
    @GetMapping("/gastos")
    public ResponseEntity<List<PessoaNomePeriodoHorasDto>> getAllPessoaPeriodoHorasGastas(@RequestBody @Valid PessoaNomePeriodoForm pessoaNomePeriodoForm) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAllPessoaPeriodoHorasGastas(pessoaNomePeriodoForm));
    }

}
