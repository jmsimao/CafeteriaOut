package br.com.josemarcelo.CafeteriaOut.Model;

import java.util.UUID;

public class Cafe {
	
	private final String Id;
	private String nome;
	
	public Cafe(String id, String nome) {
		super();
		this.Id = UUID.randomUUID().toString();
		this.nome = nome;
	}
	
	public Cafe(String nome) {
		this.Id = UUID.randomUUID().toString();
		this.nome = nome;
	}
		
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getId() {
		return Id;
	}

}
