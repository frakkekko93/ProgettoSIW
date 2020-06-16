package it.uniroma3.siw.progetto.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Utente;

@Repository
public interface ProgettoRepository extends CrudRepository<Progetto, Long>
{
	/* Lista dei progetti creati da un utente */
	public List<Progetto> findByProprietario(Utente proprietario);
	
	/* Lista dei progetti condivisi con me */
	public List<Progetto> findByMembri(Utente membri);
}
