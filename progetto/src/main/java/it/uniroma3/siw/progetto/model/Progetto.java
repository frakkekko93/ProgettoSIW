package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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

	private String descrizione;
	
	@Column(updatable=false, nullable=false)
	private LocalDateTime dataInizio;

	@ManyToMany(fetch=FetchType.LAZY)
	private List<Utente> membri;

	@ManyToOne(fetch=FetchType.EAGER)
	private Utente proprietario;
	
	@OneToMany(mappedBy="progetto", fetch=FetchType.EAGER, cascade= {CascadeType.REMOVE})
	private List<Task> tasks;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Tag> tags;
	
	/* Costruttore */
	public Progetto()
	{
		this.membri = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tags = new ArrayList<>();
		this.dataInizio = LocalDateTime.now();
	}
	
	public void aggiungiMembro(Utente utente) 
	{
		if(!this.membri.contains(utente))
			this.membri.add(utente);
	}
	
	/* Setters and getters */
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

	public String getDescrizione() 
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	public List<Utente> getMembri() 
	{
		return membri;
	}

	public void setMembri(List<Utente> membri) 
	{
		this.membri = membri;
	}

	public Utente getProprietario() 
	{
		return proprietario;
	}

	public void setProprietario(Utente proprietario) 
	{
		this.proprietario = proprietario;
	}

	public List<Task> getTasks() 
	{
		return tasks;
	}

	public void setTasks(List<Task> tasks) 
	{
		this.tasks = tasks;
	}

	public List<Tag> getTags() 
	{
		return tags;
	}

	public void setTags(List<Tag> tags) 
	{
		this.tags = tags;
	}
}
