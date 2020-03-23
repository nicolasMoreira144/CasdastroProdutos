package com.souza.service;

import java.util.List;
import java.util.Optional;

import com.souza.entities.Produto;

public interface ProdutoService {
	
	void salvar(Produto produto);
	
	void editar(Produto produto);
	
	void excluir(Long id);
	
	Optional<Produto> buscarPorId(Long id);
	
	List<Produto> buscarTodos();
}
