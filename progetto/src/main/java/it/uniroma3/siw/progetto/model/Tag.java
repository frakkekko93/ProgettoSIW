package it.uniroma3.siw.progetto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(nullable = false)
	private String nome;
	
	private String colore;
	
	private String descrizione;
	
	/* Costruttore */
	public Tag()
	{
		
	}

	/* Setters and getters */
	public Long getId() 
	{
		return Id;
	}

	public void setId(Long id) 
	{
		Id = id;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getColore() 
	{
		return colore;
	}

	public void setColore(String colore) 
	{
		this.colore = colore;
	}

	public String getDescrizione() 
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(this == obj) return true;
		
		if(obj == null) return false;
		
		if(getClass() != obj.getClass()) return false;
		
		Tag other = (Tag) obj;
		if(nome == null) 
		{
			if (other.nome != null) return false;
		} 
		else if(!nome.equals(other.nome)) return false;
		
		return true;
	}
}
