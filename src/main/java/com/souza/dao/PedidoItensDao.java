package com.souza.dao;

import java.util.List;

import com.souza.entities.PedidoItens;

public interface PedidoItensDao {
	
	void save(PedidoItens pedidoItens);

    void update(PedidoItens pedidoItens);

    void delete(Long id);

    PedidoItens findById(Long id);

    List<PedidoItens> findAll();

}
