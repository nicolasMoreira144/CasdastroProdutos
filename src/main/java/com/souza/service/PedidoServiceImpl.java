package com.souza.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.dao.PedidoDao;
import com.souza.entities.Pedido;

@Service @Transactional
public class PedidoServiceImpl implements PedidoService{
	
private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	
	@Autowired
	private PedidoDao dao;
	
	@Override
	public void salvar(Pedido pedido) {
		dao.save(pedido);		
	}

	@Override
	public void editar(Pedido pedido) {
		dao.update(pedido);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Pedido> buscarPorId(Long id) {
		log.info("Buscando cartao por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Pedido> buscarTodos() {
		
		return dao.findAll();
	}

}
