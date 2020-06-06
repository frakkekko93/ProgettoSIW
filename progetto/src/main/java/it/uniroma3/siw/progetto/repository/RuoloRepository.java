package it.uniroma3.siw.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.progetto.model.Ruolo;

public interface RuoloRepository extends CrudRepository<Ruolo, Long>
{
	public Optional<Ruolo> findByUsername(String username);
}
