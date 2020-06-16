package it.uniroma3.siw.progetto.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;

@Repository
public interface TaskRepository extends CrudRepository <Task, Long>
{
	/* Ritorna la lista di task assegnati ad un utente */
	public List<Task> findByResponsabile(Utente utente);
}
