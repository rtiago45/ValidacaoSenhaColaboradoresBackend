package com.netdeal.colaboradores.restapi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private String cargo;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(name = "forcaSenha")
	private int forcaSenha;

	
	
	public Colaborador() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Colaborador(Long id, String nome, String cargo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.senha = senha;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public int getForcaSenha() {
		return forcaSenha;
	}

	public void setForcaSenha(int forcaSenha) {
		this.forcaSenha = forcaSenha;
	}

	
	@Override
	public String toString() {
		return "Colaborador [id=" + id + ", nome=" + nome + ", cargo=" + cargo + ", senha=" + senha
				 + " ]";
	}

	
}
