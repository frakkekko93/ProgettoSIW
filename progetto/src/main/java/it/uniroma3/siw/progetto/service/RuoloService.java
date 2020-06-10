package it.uniroma3.siw.progetto.service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.RuoloRepository;

@Service
public class RuoloService 
{
	@Autowired
	protected RuoloRepository ruoloRepository;
	
	/* Trova un ruolo in base all'username dell'utente che vi è associato */
	@Transactional
	public Ruolo getRuolo(String username)
	{
		UtenteService us = new UtenteService();
		
		if((username.isEmpty()) || username == null)
		{
			return null;
		}
		
		Utente u = us.findByUsername(username);
		
		Optional<Ruolo> result = this.ruoloRepository.findByUser(u);
		
		return result.orElse(null);
	}
	
	/* Salva un ruolo nel db */
	@Transactional
	public void save(Ruolo r)
	{
		this.ruoloRepository.save(r);
	}
}
