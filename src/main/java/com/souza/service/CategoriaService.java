package com.souza.service;

import java.util.List;
import java.util.Optional;

import com.souza.entities.Categoria;

public interface CategoriaService {

	void salvar(Categoria categoria);

	void editar(Categoria categoria);

	void excluir(Long id);

	Optional<Categoria> buscarPorId(Long id);

	List<Categoria> buscarTodos();

}
