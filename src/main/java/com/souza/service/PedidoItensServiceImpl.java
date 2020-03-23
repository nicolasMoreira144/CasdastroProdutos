package com.souza.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.dao.PedidoItensDao;
import com.souza.entities.Pedido;
import com.souza.entities.PedidoItens;

@Service @Transactional
public class PedidoItensServiceImpl implements PedidoItensService{
	
private static final Logger log = LoggerFactory.getLogger(PedidoItensServiceImpl.class);
	
	@Autowired
	private PedidoItensDao dao;
	
	@Override
	public void salvar(PedidoItens pedidoItens) {
		dao.save(pedidoItens);		
	}

	@Override
	public void editar(PedidoItens pedidoItens) {
		dao.update(pedidoItens);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<PedidoItens> buscarPorId(Long id) {
		log.info("Buscando itens do pedido pelo Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<PedidoItens> buscarTodos() {
		
		return dao.findAll();
	}

}
