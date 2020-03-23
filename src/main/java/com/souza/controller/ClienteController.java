package com.souza.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
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

import com.souza.dtos.ClienteDto;
import com.souza.entities.Cliente;
import com.souza.response.Response;
import com.souza.service.ClienteService;



@Controller
@CrossOrigin
@RequestMapping("/clientes")
public class ClienteController {
	
private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/inserir")
	public ResponseEntity<Response<ClienteDto>> cadastrar(@Valid @RequestBody ClienteDto clienteDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando cliente: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();

		Cliente cliente = this.converterDtoParaCliente(clienteDto);

		if (result.hasErrors()) {
			log.error("Erro validando dados do cliente : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.clienteService.salvar(cliente);

		response.setData(clienteDto);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<ClienteDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando cliente por id : {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();
		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);
		
		
		if (!cliente.isPresent()) {
			log.info("Erro ao buscar devido cliente ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. cliente não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Cliente clientePesquisado = cliente.get();
		ClienteDto clienteDto = this.clienteDto(clientePesquisado);
		
		response.setData(clienteDto);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Response<ClienteDto>> editar(@PathVariable("id") Long id, @Valid @RequestBody ClienteDto clienteDto, BindingResult result) {
		log.info("Editando cliente por id: {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();
		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);
		
		
		if (!cliente.isPresent()) {
			log.info("Erro ao editar devido cliente ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. cliente não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		if (result.hasErrors()) {
			log.error("Erro validando dados do Cliente : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Cliente clientePesquisado = cliente.get();
		clientePesquisado = converterDtoParaCliente(clienteDto);
		clienteService.editar(clientePesquisado);
		
		response.setData(clienteDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/todos")
	public ResponseEntity<Response<ClienteDto>> buscar() {
		log.info("Listando todos clientes : ");
		Response<ClienteDto> response = new Response<ClienteDto>();
		
		List<Cliente> listCliente = this.clienteService.buscarTodos();
		List<ClienteDto> listClienteDto = new ArrayList<>();
		int indice = -1;
		for(Cliente c : listCliente) {
			listClienteDto.add(++indice, clienteDto(c));   
		}
		
		response.setListData(listClienteDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo cliente: {}", id);
		Response<String> response = new Response<String>();
		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Erro ao remover devido cliente ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.excluir(id);
		log.info("Cliente removido: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	
	
	private ClienteDto clienteDto (Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
		clienteDto.setNome(cliente.getNome());
		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setSenha(cliente.getSenha());
		clienteDto.setRua(cliente.getRua());
		clienteDto.setCidade(cliente.getCidade());
		clienteDto.setBairro(cliente.getBairro());
		clienteDto.setCep(cliente.getCep());
		clienteDto.setEstado(cliente.getEstado());
		return clienteDto;
	}
	
	private Cliente converterDtoParaCliente(ClienteDto clienteDto){
		Cliente cliente = new Cliente();
		cliente.setId(clienteDto.getId());
		cliente.setNome(clienteDto.getNome());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setSenha(clienteDto.getSenha());
		cliente.setRua(clienteDto.getRua());
		cliente.setCidade(clienteDto.getCidade());
		cliente.setBairro(clienteDto.getBairro());
		cliente.setCep(clienteDto.getCep());
		cliente.setEstado(clienteDto.getEstado());
		return cliente;
	}
}
