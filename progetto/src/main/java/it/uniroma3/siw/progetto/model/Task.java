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
import javax.persistence.OneToOne;

@Entity
public class Task 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String descrizione;
	
	@Column(updatable=false, nullable=false)
	private LocalDateTime dataCreazione;

	@ManyToOne
	private Progetto progetto;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Tag> tags;
	
	@OneToOne
	private Utente responsabile;
	
	/* Costruttore */
	public Task() 
	{
		this.tags = new ArrayList<>();
		this.dataCreazione = LocalDateTime.now();
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

	public Progetto getProgetto() 
	{
		return progetto;
	}

	public void setProgetto(Progetto progetto) 
	{
		this.progetto = progetto;
	}

	public List<Tag> getTags() 
	{
		return tags;
	}

	public void setTags(List<Tag> tags) 
	{
		this.tags = tags;
	}

	public Utente getResponsabile() 
	{
		return responsabile;
	}

	public void setResponsabile(Utente responsabile) 
	{
		this.responsabile = responsabile;
	}
}
