package com.souza.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.dao.CategoriaDao;
import com.souza.entities.Categoria;

@Service @Transactional
public class CategoriaServiceImpl implements CategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private CategoriaDao dao;
	
	@Override
	public void salvar(Categoria categoria) {
		dao.save(categoria);		
	}

	@Override
	public void editar(Categoria categoria) {
		dao.update(categoria);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Categoria> buscarPorId(Long id) {
		log.info("Buscando categoria por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Categoria> buscarTodos() {
		
		return dao.findAll();
	}
	

}
