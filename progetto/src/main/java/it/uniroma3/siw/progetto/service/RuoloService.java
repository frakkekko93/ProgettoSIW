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
	public Ruolo getRuolo(Utente u)
	{
		Optional<Ruolo> r = this.ruoloRepository.findByUtente(u);

		return r.orElse(null);
	}

	/* Salva un ruolo nel db */
	@Transactional
	public Ruolo save(Ruolo r)
	{
		return this.ruoloRepository.save(r);
	}
	
	/* Cancella un ruolo dal db */
	@Transactional
	public void delete(Ruolo r)
	{
		this.ruoloRepository.delete(r);
	}
}
