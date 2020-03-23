package com.souza.dao;

import java.util.List;

import com.souza.entities.Produto;

public interface ProdutoDao {
	
	void save(Produto produto);

    void update(Produto produto);

    void delete(Long id);

    Produto findById(Long id);

    List<Produto> findAll();

}
