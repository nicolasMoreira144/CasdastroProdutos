package com.souza.dao;

import java.util.List;

import com.souza.entities.Cliente;

public interface ClienteDao {
	
	void save(Cliente cliente);

    void update(Cliente cliente);

    void delete(Long id);

    Cliente findById(Long id);

    List<Cliente> findAll();

}
