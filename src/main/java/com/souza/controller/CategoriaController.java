package com.souza.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.souza.dtos.CategoriaDto;
import com.souza.dtos.ClienteDto;
import com.souza.entities.Categoria;
import com.souza.entities.Cliente;
import com.souza.response.Response;
import com.souza.service.CategoriaService;



@Controller
@CrossOrigin
@RequestMapping("/categorias")
public class CategoriaController {
	
private static final Logger log = LoggerFactory.getLogger(CategoriaController.class);
	
	@Autowired
	private CategoriaService categoriaService;
	
	@PostMapping("/inserir")
	public ResponseEntity<Response<CategoriaDto>> cadastrar(@Valid @RequestBody CategoriaDto categoriaDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando categoria: {}", categoriaDto.toString());
		Response<CategoriaDto> response = new Response<CategoriaDto>();

		Categoria categoria = this.converterDtoParaCategoria(categoriaDto);

		if (result.hasErrors()) {
			log.error("Erro validando dados da categoria : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.categoriaService.salvar(categoria);

		response.setData(categoriaDto);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<CategoriaDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando categoria por id : {}", id);
		Response<CategoriaDto> response = new Response<CategoriaDto>();
		Optional<Categoria> categoria = this.categoriaService.buscarPorId(id);
		
		
		if (!categoria.isPresent()) {
			log.info("Erro ao buscar devido categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. categoria não encontrada com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Categoria categoriaPesquisada = categoria.get();
		CategoriaDto categoriaDto = this.categoriaDto(categoriaPesquisada);
		
		response.setData(categoriaDto);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Response<CategoriaDto>> editar(@PathVariable("id") Long id, @Valid @RequestBody CategoriaDto categoriaDto, BindingResult result) {
		log.info("Editando categoria por id: {}", id);
		Response<CategoriaDto> response = new Response<CategoriaDto>();
		Optional<Categoria> categoria = this.categoriaService.buscarPorId(id);
		
		
		if (!categoria.isPresent()) {
			log.info("Erro ao editar devido categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. categoria não encontrada com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Cliente : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Categoria categoriaPesquisado = categoria.get();
		categoriaPesquisado = converterDtoParaCategoria(categoriaDto);
		categoriaService.editar(categoriaPesquisado);
		
		response.setData(categoriaDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<Response<CategoriaDto>> buscar() {
		log.info("Listando todas categorias : ");
		Response<CategoriaDto> response = new Response<CategoriaDto>();
		
		List<Categoria> listCategoria = this.categoriaService.buscarTodos();
		List<CategoriaDto> listCategoriaDto = new ArrayList<>();
		int indice = -1;
		for(Categoria c : listCategoria) {
			listCategoriaDto.add(++indice, categoriaDto(c));   
		}
		
		response.setListData(listCategoriaDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<Categoria> categoria = this.categoriaService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Erro ao remover devido categoria ID: {} ser inválida.", id);
			response.getErrors().add("Erro ao remover categoria. Categoria não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaService.excluir(id);
		log.info("Categoria removida: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	private CategoriaDto categoriaDto (Categoria categoria) {
		CategoriaDto categoriaDto = new CategoriaDto();
		categoriaDto.setId(categoria.getId());
		categoriaDto.setCategoria(categoria.getCategoria());
		return categoriaDto;
	}
	
	private Categoria converterDtoParaCategoria(CategoriaDto categoriaDto){
		Categoria categoria = new Categoria();
		categoria.setId(categoriaDto.getId());
		categoria.setCategoria(categoriaDto.getCategoria());
		return categoria;
	}
}
