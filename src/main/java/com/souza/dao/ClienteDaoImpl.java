package com.souza.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.entities.Cliente;

@Repository
@Transactional
public class ClienteDaoImpl extends AbstractDao<Cliente, Long> implements ClienteDao {
	
}