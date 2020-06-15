package it.uniroma3.siw.progetto.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.ProgettoRepository;


@Service
public class ProgettoService 
{
	@Autowired
	protected ProgettoRepository progettoRepository;
	
	/* Salva un progetto nel db */
	@Transactional
	public Progetto save(Progetto progetto)
	{
		return this.progettoRepository.save(progetto);
	}
	
	/* Lista progetti creati da un utente */
	public List<Progetto> findByProprietario(Utente proprietario)
	{
		return this.progettoRepository.findByProprietario(proprietario);
	}
	
	/* Condividi il progetto con un membro */
	@Transactional
    public Progetto condividiProgettoConUtente(Progetto progetto, Utente utente) {
        progetto.aggiungiMembro(utente);
        return this.progettoRepository.save(progetto);
    }
}
