package com.souza.dtos;

public class ProdutoDto {
	
	private Long id;
	private Long idCategoria;
	private String produto;
	private Double preco;
	private Integer quantidade;
	private String descricao;
	private String foto;
	
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	@Override
	public String toString() {
		return "ProdutoDto [idCategoria=" + idCategoria + ", produto=" + produto + ", preco=" + preco + ", quantidade="
				+ quantidade + ", descricao=" + descricao + ", foto=" + foto + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
