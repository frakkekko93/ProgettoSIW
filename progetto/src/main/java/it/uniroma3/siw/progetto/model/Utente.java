package it.uniroma3.siw.progetto.model;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Utente 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	
	private String nome;
	
	private String cognome;
	
	private String mail;
	
	@Column(updatable=false, nullable=false)
	private LocalDateTime dataDiCreazione;
	
	@ManyToMany(mappedBy = "membri", fetch=FetchType.LAZY)
	private List<Progetto> progettiVisibili;
	
	@OneToMany(mappedBy = "proprietario", fetch=FetchType.EAGER, cascade= {CascadeType.REMOVE})
	private List<Progetto> progettiCreati;
	
	/* Costruttore */
	public Utente()
	{
		this.progettiVisibili = new ArrayList<>();
		this.progettiCreati = new ArrayList<>();
		this.dataDiCreazione = LocalDateTime.now();
	}

	/* Setters and getters */
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	
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

	public String getCognome() 
	{
		return cognome;
	}

	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public LocalDateTime getDataDiCreazione() 
	{
		return dataDiCreazione;
	}

	public void setDataDiCreazione(LocalDateTime dataDiCreazione) 
	{
		this.dataDiCreazione = dataDiCreazione;
	}

	public String getMail() 
	{
		return mail;
	}

	public void setMail(String mail) 
	{
		this.mail = mail;
	}

	public List<Progetto> getProgettiVisibili() 
	{
		return progettiVisibili;
	}

	public void setProgettiVisibili(List<Progetto> progettiVisibili) 
	{
		this.progettiVisibili = progettiVisibili;
	}

	public List<Progetto> getProgettiCreati() 
	{
		return progettiCreati;
	}

	public void setProgettiCreati(List<Progetto> progettiCreati) 
	{
		this.progettiCreati = progettiCreati;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		
		if (obj == null) return false;
		
		if (getClass() != obj.getClass()) return false;
		
		Utente other = (Utente) obj;
		
		if (username == null) 
		{
			if (other.username != null) return false;
		} 
		else if (!username.equals(other.username)) return false;
		
		return true;
	}
}
