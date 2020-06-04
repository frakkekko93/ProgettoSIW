package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Progetto
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(nullable=false)
	private String nome;

	@Column(updatable=false, nullable=false)
	private LocalDateTime dataInizio;

	@ManyToMany(fetch=FetchType.LAZY)
	private List<Utente> membri;

	@ManyToOne(fetch=FetchType.EAGER)
	private Utente proprietario;
	
	@OneToMany(mappedBy="progetto", fetch=FetchType.EAGER)
	private List<Task> tasks;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Tag> tags;
	
	/* Costruttori */
	public Progetto()
	{
		this.membri = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tags = new ArrayList<>();
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
