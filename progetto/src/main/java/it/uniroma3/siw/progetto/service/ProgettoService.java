package it.uniroma3.siw.progetto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.repository.ProgettoRepository;

@Service
public class ProgettoService 
{
	@Autowired
	protected ProgettoRepository progettoRepository;
}
