package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Task 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String descrizione;
	
	private LocalDateTime dataCreazione;

	@ManyToMany
	private List<Tag> tags;
	
	/* Costruttori */
	public Task() 
	{
		this.tags = new ArrayList<>();
	}

	public Task(String nome, String descrizione, LocalDateTime dataCreazione) 
	{
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
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

	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataCreazione() 
	{
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) 
	{
		this.dataCreazione = dataCreazione;
	}
}
