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

import com.souza.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	@Autowired
	private ClienteServiceImpl clienteService;
	
	private static final Long ID = 9L;
	private static final String BAIRRO = "Aricanduva";
	private static final String NOME = "Nicolas";
	private static final String CEP = "03579-300";
	private static final String CIDADE = "São Paulo";
	private static final String EMAIL = "nicolassouza144@gmail.com";
	private static final String ESTADO = "São Paulo";
	private static final String RUA = "Roberto josi";
	private static final String SENHA = "12345";
	
	@Before
	public void setUp() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setId(ID);
		cliente.setNome(NOME);
		cliente.setBairro(BAIRRO);
		cliente.setCep(CEP);
		cliente.setCidade(CIDADE);
		cliente.setEmail(EMAIL);
		cliente.setEstado(ESTADO);
		cliente.setRua(RUA);
		cliente.setSenha(SENHA);
		
		this.clienteService.salvar(cliente);
	}
	
	@After
    public final void tearDown() { 
		this.clienteService.excluir(ID);;
	}

	@Test
	public void testBuscarPorID() {
		Optional<Cliente> clienteOptional = this.clienteService.buscarPorId(ID);
		if(clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.get();
			assertEquals(ID, cliente.getId());
			
		}else {
			return;
		}
	}


}
