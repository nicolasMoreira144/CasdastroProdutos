package com.souza.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.souza.entities.Categoria;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoriaServiceTest {
	
	@Autowired
	private CategoriaServiceImpl categoriaService;
	
	private static final Long ID = 219L;
	private static final String CATEGORIA = "Infantil";
	
	
	@Before
	public void setUp() throws Exception {
		Categoria categoria = new Categoria();
		categoria.setId(ID);
		categoria.setCategoria(CATEGORIA);
		
		
		this.categoriaService.salvar(categoria);
	}
	
	@After
    public final void tearDown() { 
		this.categoriaService.excluir(ID);;
	}

	@Test
	public void testBuscarPorID() {
		Optional<Categoria> categoriaOptional = this.categoriaService.buscarPorId(ID);
		if(categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();
			assertEquals(ID, categoria.getId());
			
		}else {
			return;
		}
	}


}
