package it.uniroma3.siw.progetto.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;

@Repository
public interface RuoloRepository extends CrudRepository<Ruolo, Long>
{
	/* Trova il ruolo di un utente */
	public Optional<Ruolo> findByUtente(Utente utente);
}
