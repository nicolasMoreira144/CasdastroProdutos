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
import com.souza.entities.Cliente;
import com.souza.entities.Pedido;
import com.souza.response.Response;
import com.souza.service.ClienteService;
import com.souza.service.PedidoService;

@Controller
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoController {
private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/inserir")
	public ResponseEntity<Response<PedidoDto>> cadastrar(@Valid @RequestBody PedidoDto pedidoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando pedido: {}", pedidoDto.toString());
		Response<PedidoDto> response = new Response<PedidoDto>();

		Optional<Cliente> clienteOpt = this.clienteService.buscarPorId(pedidoDto.getIdCliente());
		
		if (!clienteOpt.isPresent())
			result.addError(new ObjectError("Cliente", "Cliente não existe."));
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Pedido : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Cliente cliente = clienteOpt.get();
		Pedido pedido = this.converterDtoParaPedido(pedidoDto, cliente);
		
		this.pedidoService.salvar(pedido);

		response.setData(pedidoDto);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<PedidoDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando cliente por: {}", id);
		Response<PedidoDto> response = new Response<PedidoDto>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);
		
		
		if (!pedido.isPresent()) {
			log.info("Erro ao buscar devido pedido ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. cliente não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Pedido pedidoPesquisado = pedido.get();
		PedidoDto pedidoDto = this.pedidoDto(pedidoPesquisado, pedidoPesquisado.getCliente().getId());
		
		response.setData(pedidoDto);
		return ResponseEntity.ok(response);
	
		
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Response<PedidoDto>> editar(@PathVariable("id") Long id, @Valid @RequestBody PedidoDto pedidoDto, BindingResult result) {
		log.info("Editando produto por id: {}", id);
		Response<PedidoDto> response = new Response<PedidoDto>();
		Optional<Pedido> pedidoOpt = this.pedidoService.buscarPorId(id);
		Optional<Cliente> clienteOpt = this.clienteService.buscarPorId(pedidoDto.getIdCliente());
		
		if (!clienteOpt.isPresent())
			result.addError(new ObjectError("Pedido", "ID do cliente não existe."));
		
		
		if (!pedidoOpt.isPresent()) {
			log.info("Erro ao editar devido pedido ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover pedido. cliente não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Pedido : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Cliente cliente = clienteOpt.get();
		Pedido pedidoPesquisado = pedidoOpt.get();
		pedidoPesquisado = converterDtoParaPedido(pedidoDto, cliente);
		pedidoService.editar(pedidoPesquisado);
		
		response.setData(pedidoDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<Response<PedidoDto>> buscar() {
		log.info("Listando todos pedidos : ");
		Response<PedidoDto> response = new Response<PedidoDto>();
		
		List<Pedido> listPedido = this.pedidoService.buscarTodos();
		List<PedidoDto> listPedidoDto = new ArrayList<>();
		int indice = -1;
		for(Pedido c : listPedido) {
			listPedidoDto.add(++indice, pedidoDto(c));   
		}
		
		response.setListData(listPedidoDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo pedido: {}", id);
		Response<String> response = new Response<String>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);

		if (!pedido.isPresent()) {
			log.info("Erro ao remover devido cartão ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cartão. cartão não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.pedidoService.excluir(id);
		log.info("Pedido removido: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	private PedidoDto pedidoDto (Pedido pedido, Long idCliente) {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(pedido.getId());
		pedidoDto.setData(pedido.getData());
		pedidoDto.setStatus(pedido.getStatus());
		pedidoDto.setSessao(pedido.getSessao());
		pedidoDto.setIdCliente(idCliente);
		return pedidoDto;
	}
	
	private PedidoDto pedidoDto (Pedido pedido) {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(pedido.getId());
		pedidoDto.setData(pedido.getData());
		pedidoDto.setStatus(pedido.getStatus());
		pedidoDto.setSessao(pedido.getSessao());
		pedidoDto.setIdCliente(pedido.getCliente().getId());
		return pedidoDto;
	}
	private Pedido converterDtoParaPedido(PedidoDto pedidoDto , Cliente cliente){
		Pedido pedido = new Pedido();
		pedido.setId(pedidoDto.getId());
		pedido.setData(pedidoDto.getData());
		pedido.setStatus(pedidoDto.getStatus());
		pedido.setSessao(pedidoDto.getSessao());
		pedido.setCliente(cliente);
		return pedido;
	}

}
