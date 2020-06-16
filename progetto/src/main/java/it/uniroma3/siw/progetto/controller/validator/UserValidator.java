package it.uniroma3.siw.progetto.controller.validator;

import javax.servlet.http.HttpServletRequest;

/* Classe che valida i dati di un utente */
public class UserValidator 
{
	/* Valida i dati inseriti dall'utente nella form */
	public boolean validate(HttpServletRequest request) 
	{
		boolean datiValidi = true;
		
		String nome = request.getParameter("nomeInput"); 
		String cognome = request.getParameter("cognomeInput");
		String mail = request.getParameter("mailInput");

		/* Nome obbligatorio */
		if(nome == null || nome.isEmpty()) 
		{
			datiValidi = false;
			request.setAttribute("erroreNome", "Il nome è obbligatorio");
		}

		/* Cognome obbligatorio */
		if(cognome == null || cognome.isEmpty()) 
		{
			datiValidi = false;
			request.setAttribute("erroreCognome", "Il cognome è obbligatorio");
		}
		
		/* Mail obbligatoria e valida */
		if(mail == null || mail.isEmpty() || !mail.contains("@")) 
		{
			datiValidi = false;
			request.setAttribute("erroreMail", "Indirizzo mail non valido");
		}

		return datiValidi;
	}
}
