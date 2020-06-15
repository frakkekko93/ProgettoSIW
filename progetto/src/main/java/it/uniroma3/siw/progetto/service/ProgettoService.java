package it.uniroma3.siw.progetto.service;

import java.util.List;
import java.util.Optional;
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

	/* Prende un progetto dal suo id */
	public Progetto getProgetto(Long id)
	{
		Optional<Progetto> result = this.progettoRepository.findById(id);

		return result.orElse(null);
	}

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
	
	/* Lista progetti condivisi con me */
	public List<Progetto> findByMembri(Utente membri)
	{
		return this.progettoRepository.findByMembri(membri);
	}

	/* Condividi il progetto con un membro */
	@Transactional
    public Progetto condividiProgettoConUtente(Progetto progetto, Utente utente) 
	{
        progetto.aggiungiMembro(utente);
        return this.progettoRepository.save(progetto);
    }
	
	/* Elimina progetto */
	@Transactional
	public void delete(Progetto progetto) 
	{
		this.progettoRepository.delete(progetto);
	}
}
