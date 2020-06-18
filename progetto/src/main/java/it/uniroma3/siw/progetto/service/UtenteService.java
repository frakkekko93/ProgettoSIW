package it.uniroma3.siw.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.UtenteRepository;

@Service
public class UtenteService
{
	@Autowired
	protected UtenteRepository utenteRepository;
	
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected RuoloService ruoloService;
	
	@Autowired
	protected ProgettoService progettoService;

	/* Trova un utente in base al suo id */
	public Utente getUtente(Long id)
	{
		Optional<Utente> result = this.utenteRepository.findById(id);

		return result.orElse(null);
	}

	/* Trova un utente in base al suo username */
	public Utente getUtente(String username)
	{
		Optional<Utente> result = this.utenteRepository.findByUsername(username);

		return result.orElse(null);
	}

	/* Memorizza un utente nel db */
	@Transactional
	public Utente save(Utente r)
	{
		return this.utenteRepository.save(r);
	}
	
	/* Prende tutti gli utenti presenti nel db */
    public List<Utente> getAllUsers() 
	{
        List<Utente> result = new ArrayList<>();
        Iterable<Utente> iterable = this.utenteRepository.findAll();
        for(Utente utente : iterable)
            result.add(utente);
        return result;
    }
	
	/* Elimina un utente */
	@Transactional
	public void delete(Utente utente) 
	{
		/* Elimino i collegamenti ai progetti visibili all'utente */
		this.progettoService.deleteAllProjectsMembers(utente);
		
		/* Elimino il ruolo dell'utente */
		Ruolo r = this.ruoloService.getRuolo(utente);
	    this.ruoloService.delete(r);
		
	    /* Elimino i collegamenti ai task di cui l'utente è responsabile */
		List<Task> tasks = this.taskService.getAllTasks();
		for(Task t: tasks)
		{
			if(t.getResponsabile() != null)
			{
				if(t.getResponsabile().equals(utente))
				{
					t.setResponsabile(null);
					this.taskService.save(t);
				}
			}
		}
		
		/* Elimino l'utente */
		this.utenteRepository.delete(utente);
	}
}
