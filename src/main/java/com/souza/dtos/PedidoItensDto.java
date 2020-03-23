package com.souza.dtos;

public class PedidoItensDto {
	private Long id;
	private Long id_pedido;
	private Long id_produto;
	private String nomeProduto;
	private Integer quantidade;
	private Double valor;
	private Double subTotal;
	
	
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	public Long getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
	public Long getId_produto() {
		return id_produto;
	}
	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}
	@Override
	public String toString() {
		return "PedidoItensDto [id_pedido=" + id_pedido + ", id_produto=" + id_produto + ", nomeProduto=" + nomeProduto
				+ ", quantidade=" + quantidade + ", valor=" + valor + ", subTotal=" + subTotal + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
