package com.souza.service;

import java.util.List;
import java.util.Optional;

import com.souza.entities.Pedido;

public interface PedidoService {
	
	void salvar(Pedido pedido);
	
	void editar(Pedido pedido);
	
	void excluir(Long id);
	
	Optional<Pedido> buscarPorId(Long id);
	
	List<Pedido> buscarTodos();
}

