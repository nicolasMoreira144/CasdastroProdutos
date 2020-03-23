package com.souza.service;

import java.util.List;
import java.util.Optional;

import com.souza.entities.PedidoItens;

public interface PedidoItensService {

void salvar(PedidoItens pedidoItens);
	
	void editar(PedidoItens pedidoItens);
	
	void excluir(Long id);
	
	Optional<PedidoItens> buscarPorId(Long id);
	
	List<PedidoItens> buscarTodos();

}
