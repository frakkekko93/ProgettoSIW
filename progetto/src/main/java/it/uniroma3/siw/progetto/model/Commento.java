package it.uniroma3.siw.progetto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Commento 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String autore;
	
	@Column(nullable = false)
	private String testo;
	
	@ManyToOne
	private Task task;

	/* Costruttore */
	public Commento()
	{
		
	}

	/* Getters and Setters */
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getAutore() 
	{
		return autore;
	}

	public void setAutore(String autore) 
	{
		this.autore = autore;
	}

	public String getTesto() 
	{
		return testo;
	}

	public void setTesto(String testo) 
	{
		this.testo = testo;
	}
	
	public Task getTask() 
	{
		return task;
	}

	public void setTask(Task task) 
	{
		this.task = task;
	}
}
