package com.souza.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "pedidos")
public class Pedido extends AbstractEntity<Long> {

	@ManyToOne
	@JoinColumn(name = "id_cliente_fk")
	private Cliente cliente;
	@NotNull
	private String status;
	@NotNull
	private String sessao;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "YYYY-MM-dd")
	private LocalDate data;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSessao() {
		return sessao;
	}

	public void setSessao(String sessao) {
		this.sessao = sessao;
	}

}
