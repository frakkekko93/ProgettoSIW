package it.uniroma3.siw.progetto.controller.validator;



import javax.servlet.http.HttpServletRequest;



public class UserValidator {

	public boolean validate(HttpServletRequest request) {

		boolean datiValidi = true;
		
		String nome = request.getParameter("nomeInput"); 
		String cognome = request.getParameter("cognomeInput");
		String mail = request.getParameter("mailInput");

		//controllo dei dati
		if(nome == null || nome.isEmpty()) {
			
			datiValidi = false;
			request.setAttribute("erroreNome", "Il nome è obbligatorio");
		}

		if(cognome == null || cognome.isEmpty()) {
			datiValidi = false;
			request.setAttribute("erroreCognome", "Il cognome è obbligatorio");
		}
		
		if(mail == null || mail.isEmpty() || !mail.contains("@")) {
			datiValidi = false;
			request.setAttribute("erroreMail", "Indirizzo mail non valido");
		}

		return datiValidi;

		}

}
