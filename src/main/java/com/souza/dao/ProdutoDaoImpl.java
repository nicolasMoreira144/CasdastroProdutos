package com.souza.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.entities.Produto;

@Repository
@Transactional
public class ProdutoDaoImpl extends AbstractDao<Produto, Long> implements ProdutoDao {
	
}

