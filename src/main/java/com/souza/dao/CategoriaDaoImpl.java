package com.souza.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.entities.Categoria;

@Repository
@Transactional
public class CategoriaDaoImpl extends AbstractDao<Categoria, Long> implements CategoriaDao {
	
}