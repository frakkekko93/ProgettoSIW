package it.uniroma3.siw.progetto.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.ProgettoRepository;

@Service
public class ProgettoService
{
	@Autowired
	protected ProgettoRepository progettoRepository;
	
	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected TaskService taskService;

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
	
	/* Elimina un tag dal progetto*/
	@Transactional
	public void deleteTag(Tag tag, Progetto progetto)
	{
		/* Prendo la lista dei task del progetto */
		List<Task> listaTask = progetto.getTasks();
		Iterator<Task> it = listaTask.iterator();
		
		/* Elimino il tag da tutti i task del progetto */
		Task t = null;
		while(it.hasNext())
		{
			t = it.next();
			
			this.taskService.deleteTag(tag, t);
		}
		
		progetto.getTags().remove(tag);
		this.progettoRepository.save(progetto);
	}
	
	/* Elimina tutti i membri dei progetti di un utente */
	@Transactional
	public void deleteAllProjectsMembers(Utente utente)
	{
		/* Elimino i collegamenti ai progetti visibili */
		List<Progetto> visibili = utente.getProgettiVisibili();
	    Iterator<Progetto> iterator = visibili.iterator();
	    
	    while(iterator.hasNext()) 
	    {
	    	Progetto p = iterator.next();
	    	p.getMembri().clear();
	    	this.progettoRepository.save(p);
	    }
	}
	
	/* Elimina un membro da un progetto */
	public Progetto deleteMembro(Progetto progetto, Utente membro)
	{
		progetto.getMembri().remove(membro);
		membro.getProgettiVisibili().remove(progetto);
		this.utenteService.save(membro);
		return this.save(progetto);
	}
}
