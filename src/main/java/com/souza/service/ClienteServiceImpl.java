package com.souza.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.dao.ClienteDao;
import com.souza.entities.Cliente;

@Service @Transactional
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteDao dao;
	
	@Override
	public void salvar(Cliente cliente) {
		dao.save(cliente);		
	}

	@Override
	public void editar(Cliente cliente) {
		dao.update(cliente);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Cliente> buscarPorId(Long id) {
		log.info("Buscando cartao por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Cliente> buscarTodos() {
		
		return dao.findAll();
	}
	

}
