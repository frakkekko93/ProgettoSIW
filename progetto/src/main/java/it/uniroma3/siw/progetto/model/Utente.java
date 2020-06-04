package it.uniroma3.siw.progetto.model;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private String nome;
	
	private String cognome;
	
	private LocalDateTime dataDiCreazione;
	
	@ManyToMany(mappedBy = "membri")
	private List<Progetto> progettiVisibili;
	
	@OneToMany(mappedBy = "proprietari")
	private List<Progetto> progettiCreati;
	
	public Utente()
	{
		this.progettiVisibili = new ArrayList<>();
		this.progettiCreati = new ArrayList<>();
	}
	
	public Utente(String username, String password, String nome, String cognome, LocalDateTime dataDiCreazione)
	{
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiCreazione = dataDiCreazione;
		
	}

	public Long getId() 
	{
		return Id;
	}

	public void setId(Long id) 
	
	{
		Id = id;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getCognome() 
	{
		return cognome;
	}

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}

	public LocalDateTime getDataDiCreazione() 
	{
		return dataDiCreazione;
	}

	public void setDataDiCreazione(LocalDateTime dataDiCreazione) 
	{
		this.dataDiCreazione = dataDiCreazione;
	}
	
	
	
}
