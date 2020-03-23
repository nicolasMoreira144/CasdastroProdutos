package com.souza.dao;

import java.util.List;

import com.souza.entities.Categoria;

public interface CategoriaDao {
	
	void save(Categoria categoria);

    void update(Categoria categoria);

    void delete(Long id);

    Categoria findById(Long id);

    List<Categoria> findAll();

}
