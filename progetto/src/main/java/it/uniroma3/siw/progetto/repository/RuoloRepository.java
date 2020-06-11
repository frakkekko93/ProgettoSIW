package it.uniroma3.siw.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;

public interface RuoloRepository extends CrudRepository<Ruolo, Long>
{
	/* Trova il ruolo di un utente */
	public Optional<Ruolo> findByUser(Utente user);
}
