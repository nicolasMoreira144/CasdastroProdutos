package com.souza.service;

import java.util.List;
import java.util.Optional;

import com.souza.entities.Cliente;

public interface ClienteService {
	
	void salvar(Cliente cliente);
	
	void editar(Cliente cliente);
	
	void excluir(Long id);
	
	Optional<Cliente> buscarPorId(Long id);
	
	List<Cliente> buscarTodos();
}
