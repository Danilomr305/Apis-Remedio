package com.remedios.danil.curso.controllers;

import com.remedios.danil.curso.remedio.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/remedios")
public class RemedioController {
	
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
    @Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados, UriComponentsBuilder uriBuilder) {
		var remedio = new Remedio(dados);
		repository.save(remedio);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();

		return  ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}

	@GetMapping
	public ResponseEntity <List<DadosListagemRemedio>> list() {
		var lista = repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();

		return  ResponseEntity.ok(lista);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizarRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);

		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();

		return ResponseEntity.noContent().build();
	}


    //Esse comando serve para reativer remedios que foram arquivado
	@PutMapping("reativar/{id}")
	@Transactional
	public ResponseEntity<Void> Reativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.reativar();

		return ResponseEntity.noContent().build();
	}

}
