package com.souza.dtos;

public class CategoriaDto {
	
	private Long id;
	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CategoriaDto [id=" + id + ", categoria=" + categoria + "]";
	}
	
	
}
