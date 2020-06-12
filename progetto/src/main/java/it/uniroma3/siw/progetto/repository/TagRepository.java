package it.uniroma3.siw.progetto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.progetto.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>
{

}
