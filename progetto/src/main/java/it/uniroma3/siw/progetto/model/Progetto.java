package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Progetto 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	private LocalDateTime dataInizio;

	/* Costruttori */
	public Progetto() 
	{
		
	}

	public Progetto(String nome, LocalDateTime dataInizio) 
	{
		this.nome = nome;
		this.dataInizio = dataInizio;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public LocalDateTime getDataInizio() 
	{
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) 
	{
		this.dataInizio = dataInizio;
	}
}
