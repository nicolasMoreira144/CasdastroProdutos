package com.souza.dao;

import java.util.List;

import com.souza.entities.Pedido;

public interface PedidoDao {
	
	void save(Pedido pedido);

    void update(Pedido pedido);

    void delete(Long id);

    Pedido findById(Long id);

    List<Pedido> findAll();

}
