package it.uniroma3.siw.progetto.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.progetto.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> 
{
	/* Trova un utente in base al suo username */
	public Optional<Utente> findByUsername(String username);
}
