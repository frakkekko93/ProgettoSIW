package it.uniroma3.siw.progetto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Ruolo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    	
	@Column(nullable=false)
	private String ruolo;
	
	@OneToOne
	private Utente utente;

	/* Costruttore */
	public Ruolo() 
	{
		
	}

	/* Getters/Setters */
	public Long getId() 
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getRuolo() 
	{
		return ruolo;
	}

	public void setRuolo(String role) 
	{
		this.ruolo = role;
	}

	public Utente getUtente() 
	{
		return utente;
	}

	public void setUtente(Utente user) 
	{
		this.utente = user;
	}
	
	public void setDefaultRole()
	{
		this.ruolo = "DEFAULT";
	}
	
	public void setAdmin()
	{
		this.ruolo = "ADMIN";
	}
}
