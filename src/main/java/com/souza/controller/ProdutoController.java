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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.souza.dtos.PedidoDto;
import com.souza.dtos.ProdutoDto;
import com.souza.entities.Categoria;
import com.souza.entities.Cliente;
import com.souza.entities.Pedido;
import com.souza.entities.Produto;
import com.souza.response.Response;
import com.souza.service.CategoriaService;
import com.souza.service.ProdutoService;

@Controller
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {
private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@PostMapping("/inserir")
	public ResponseEntity<Response<ProdutoDto>> cadastrar(@Valid @RequestBody ProdutoDto produtoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando produto: {}", produtoDto.toString());
		Response<ProdutoDto> response = new Response<ProdutoDto>();

		Optional<Categoria> categoriaOpt = this.categoriaService.buscarPorId(produtoDto.getIdCategoria());
		
		if (!categoriaOpt.isPresent())
			result.addError(new ObjectError("Produto", "Categoria não existe."));
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Produto : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Categoria categoria = categoriaOpt.get();
		Produto produto = this.converterDtoParaProduto(produtoDto, categoria);
		
		this.produtoService.salvar(produto);

		response.setData(produtoDto);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<ProdutoDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando produto por: {}", id);
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		Optional<Produto> produto = this.produtoService.buscarPorId(id);
		
		
		if (!produto.isPresent()) {
			log.info("Erro ao buscar devido produto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. cliente não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Produto produtoPesquisado = produto.get();
		ProdutoDto pedidoDto = this.produtoDto(produtoPesquisado, produtoPesquisado.getCategoria().getId());
		
		response.setData(pedidoDto);
		return ResponseEntity.ok(response);
	
		
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Response<ProdutoDto>> editar(@PathVariable("id") Long id, @Valid @RequestBody ProdutoDto produtoDto, BindingResult result) {
		log.info("Editando produto por id: {}", id);
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		Optional<Produto> produtoOpt = this.produtoService.buscarPorId(id);
		Optional<Categoria> categoriaOpt = this.categoriaService.buscarPorId(produtoDto.getIdCategoria());
		
		if (!categoriaOpt.isPresent())
			result.addError(new ObjectError("Produto", "ID da categoria não existe."));
		
		
		if (!produtoOpt.isPresent()) {
			log.info("Erro ao editar devido produto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover produto. produto não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Produto : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Categoria categoria = categoriaOpt.get();
		Produto produtoPesquisado = produtoOpt.get();
		produtoPesquisado = converterDtoParaProduto(produtoDto, categoria);
		produtoService.editar(produtoPesquisado);
		
		response.setData(produtoDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<Response<ProdutoDto>> buscar() {
		log.info("Listando todos produtos : ");
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		
		List<Produto> listProduto = this.produtoService.buscarTodos();
		List<ProdutoDto> listProdutoDto = new ArrayList<>();
		int indice = -1;
		for(Produto p : listProduto) {
			listProdutoDto.add(++indice, produtoDto(p));   
		}
		
		response.setListData(listProdutoDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo produto: {}", id);
		Response<String> response = new Response<String>();
		Optional<Produto> produto = this.produtoService.buscarPorId(id);

		if (!produto.isPresent()) {
			log.info("Erro ao remover devido produto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover produto. produto não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.excluir(id);
		log.info("Produto removido: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	private ProdutoDto produtoDto (Produto produto, Long idCategoria) {
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setId(produto.getId());
		produtoDto.setIdCategoria(idCategoria);
		produtoDto.setDescricao(produto.getDescricao());
		produtoDto.setFoto(produto.getFoto());
		produtoDto.setPreco(produto.getPreco());
		produtoDto.setProduto(produto.getProduto());
		produtoDto.setQuantidade(produto.getQuantidade());
		return produtoDto;
	}
	
	private ProdutoDto produtoDto (Produto produto) {
		
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setId(produto.getId());
		produtoDto.setIdCategoria(produto.getCategoria().getId());
		produtoDto.setDescricao(produto.getDescricao());
		produtoDto.setFoto(produto.getFoto());
		produtoDto.setPreco(produto.getPreco());
		produtoDto.setProduto(produto.getProduto());
		produtoDto.setQuantidade(produto.getQuantidade());
		
		return produtoDto;
		
		
	}
	private Produto converterDtoParaProduto(ProdutoDto produtoDto , Categoria categoria){
		Produto produto = new Produto();
		produto.setId(produtoDto.getId());
		produto.setCategoria(categoria);
		produto.setDescricao(produtoDto.getDescricao());
		produto.setFoto(produtoDto.getFoto());
		produto.setPreco(produtoDto.getPreco());
		produto.setProduto(produtoDto.getProduto());
		produto.setQuantidade(produtoDto.getQuantidade());
		
		return produto;
	}

}
