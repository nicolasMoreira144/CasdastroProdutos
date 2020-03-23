package com.souza.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.entities.Pedido;

@Repository
@Transactional
public class PedidoDaoImpl extends AbstractDao<Pedido, Long> implements PedidoDao {
	
}
