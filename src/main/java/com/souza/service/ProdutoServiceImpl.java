package com.souza.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.dao.ProdutoDao;
import com.souza.entities.Produto;

@Service @Transactional
public class ProdutoServiceImpl implements ProdutoService{
	
private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);
	
	@Autowired
	private ProdutoDao dao;
	
	@Override
	public void salvar(Produto produto) {
		dao.save(produto);		
	}

	@Override
	public void editar(Produto produto) {
		dao.update(produto);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Produto> buscarPorId(Long id) {
		log.info("Buscando produto por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Produto> buscarTodos() {
		
		return dao.findAll();
	}

}
