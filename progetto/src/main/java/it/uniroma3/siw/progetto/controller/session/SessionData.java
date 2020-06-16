package it.uniroma3.siw.progetto.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.RuoloRepository;
import it.uniroma3.siw.progetto.repository.UtenteRepository;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData 
{
	private Utente utente;
	
	private Ruolo ruolo;
	
	@Autowired
	private RuoloRepository ruoloRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	/* Ritorna il ruolo dell'utente loggato nella sessione */
	public Ruolo getLoggedRole(@AuthenticationPrincipal OAuth2User principal) 
	{
        if (this.ruolo == null)
            this.update(principal);
        return this.ruolo;
    }
	
	/* Ritorna l'utente loggato nella sessione */
	public Utente getLoggedUser(@AuthenticationPrincipal OAuth2User principal) 
	{
        if (this.utente == null)
            this.update(principal);
        return this.utente;
    }
	
	/* Aggiorna i dati di ruolo e utente loggati nella sessione */
	private void update(@AuthenticationPrincipal OAuth2User principal) 
	{	
		this.utente = utenteRepository.findByUsername(principal.getAttribute("login")).get();
		this.ruolo = ruoloRepository.findByUtente(utente).get();	
    }
}
