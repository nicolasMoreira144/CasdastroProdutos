package com.souza.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.entities.PedidoItens;

@Repository
@Transactional
public class PedidoItensDaoImpl extends AbstractDao<PedidoItens, Long> implements PedidoItensDao {
	
}

