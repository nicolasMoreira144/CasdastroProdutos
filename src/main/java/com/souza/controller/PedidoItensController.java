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

import com.souza.dtos.PedidoItensDto;
import com.souza.entities.Pedido;
import com.souza.entities.PedidoItens;
import com.souza.entities.Produto;
import com.souza.response.Response;
import com.souza.service.PedidoItensService;
import com.souza.service.PedidoService;
import com.souza.service.ProdutoService;

@Controller
@CrossOrigin
@RequestMapping("/pedidoItens")
public class PedidoItensController {
private static final Logger log = LoggerFactory.getLogger(PedidoItensController.class);
	
	@Autowired
	private PedidoItensService pedidoItensService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping("/inserir")
	public ResponseEntity<Response<PedidoItensDto>> cadastrar(@Valid @RequestBody PedidoItensDto pedidoItensDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando itens do pedido: {}", pedidoItensDto.toString());
		Response<PedidoItensDto> response = new Response<PedidoItensDto>();

		Optional<Pedido> pedidoOpt = this.pedidoService.buscarPorId(pedidoItensDto.getId_pedido());
		Optional<Produto> produtoOpt = this.produtoService.buscarPorId(pedidoItensDto.getId_produto());
		
		if (!pedidoOpt.isPresent())
			result.addError(new ObjectError("PedidoItens", "pedido não existe."));
		
		if (!produtoOpt.isPresent())
			result.addError(new ObjectError("PedidoItens", "produto não existe."));
		
		if (result.hasErrors()) {
			log.error("Erro validando dados itens do produto : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Pedido pedido = pedidoOpt.get();
		Produto produto = produtoOpt.get();
		PedidoItens pedidoItens = this.converterDtoParaPedidoItens(pedidoItensDto, pedido, produto);
		
		this.pedidoItensService.salvar(pedidoItens);

		response.setData(pedidoItensDto);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<PedidoItensDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando itens do pedido: {}", id);
		Response<PedidoItensDto> response = new Response<PedidoItensDto>();
		Optional<PedidoItens> pedidoItensOpt = this.pedidoItensService.buscarPorId(id);
		
		
		if (!pedidoItensOpt.isPresent()) {
			log.info("Erro ao buscar devido pedido itens ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover pedido itens. pedido itens não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		PedidoItens pedidoItensPesquisado = pedidoItensOpt.get();
		PedidoItensDto pedidoItensDto = this.pedidoItensDto(pedidoItensPesquisado);
		
		response.setData(pedidoItensDto);
		return ResponseEntity.ok(response);
	
		
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Response<PedidoItensDto>> editar(@PathVariable("id") Long id, @Valid @RequestBody PedidoItensDto pedidoItensDto, BindingResult result) {
		log.info("Editando itens do pedido por id: {}", id);
		Response<PedidoItensDto> response = new Response<PedidoItensDto>();
		Optional<PedidoItens> pedidoItensOpt = this.pedidoItensService.buscarPorId(id);
		Optional<Pedido> pedidoOpt = this.pedidoService.buscarPorId(pedidoItensDto.getId_pedido());
		Optional<Produto> produtoOpt = this.produtoService.buscarPorId(pedidoItensDto.getId_produto());
		
		if (!pedidoItensOpt.isPresent()) {
			result.addError(new ObjectError("PedidoItens", "pedido itens não existe."));
		}
		
		if (!pedidoOpt.isPresent())
			result.addError(new ObjectError("PedidoItens", "pedido não existe."));
		
		if (!produtoOpt.isPresent())
			result.addError(new ObjectError("PedidoItens", "produto não existe."));
		
		
		if (result.hasErrors()) {
			log.error("Erro validando itens do pedido : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Produto produto = produtoOpt.get();
		Pedido pedido = pedidoOpt.get();
		PedidoItens pedidoItensPesquisado = pedidoItensOpt.get();
		pedidoItensPesquisado = converterDtoParaPedidoItens(pedidoItensDto, pedido, produto );
		pedidoItensService.editar(pedidoItensPesquisado);
		
		response.setData(pedidoItensDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<Response<PedidoItensDto>> buscar() {
		log.info("Listando todos itens pedidos : ");
		Response<PedidoItensDto> response = new Response<PedidoItensDto>();
		
		List<PedidoItens> listPedidoItens = this.pedidoItensService.buscarTodos();
		List<PedidoItensDto> listProdutoDto = new ArrayList<>();
		int indice = -1;
		for(PedidoItens p : listPedidoItens) {
			listProdutoDto.add(++indice, pedidoItensDto(p));   
		}
		
		response.setListData(listProdutoDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo todos itens pedido: {}", id);
		Response<String> response = new Response<String>();
		Optional<PedidoItens> pedidoItens = this.pedidoItensService.buscarPorId(id);

		if (!pedidoItens.isPresent()) {
			log.info("Erro ao remover devido itens pedido ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover itens pedido. itens pedido não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.pedidoItensService.excluir(id);
		log.info("Pedidos itens removido: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	private PedidoItensDto pedidoItensDto (PedidoItens pedidoItens) {
		PedidoItensDto pedidoItensDto = new PedidoItensDto();	
		pedidoItensDto.setId(pedidoItens.getId());
		pedidoItensDto.setId_pedido(pedidoItens.getPedido().getId());
		pedidoItensDto.setId_produto(pedidoItens.getProduto().getId());
		pedidoItensDto.setNomeProduto(pedidoItens.getNomeProduto());
		pedidoItensDto.setQuantidade(pedidoItens.getQuantidade());
		pedidoItensDto.setSubTotal(pedidoItens.getSubTotal());
		pedidoItensDto.setValor(pedidoItens.getValor());
		return pedidoItensDto;
	}
	
	
	private PedidoItens converterDtoParaPedidoItens(PedidoItensDto pedidoItensDto , Pedido pedido, Produto produto){
		PedidoItens pedidoItens = new PedidoItens();	
		pedidoItens.setId(pedidoItensDto.getId());
		pedidoItens.setPedido(pedido);
		pedidoItens.setProduto(produto);
		pedidoItens.setNomeProduto(pedidoItensDto.getNomeProduto());
		pedidoItens.setQuantidade(pedidoItensDto.getQuantidade());
		pedidoItens.setSubTotal(pedidoItensDto.getSubTotal());
		pedidoItens.setValor(pedidoItensDto.getValor());
		
		return pedidoItens;
	}

}
