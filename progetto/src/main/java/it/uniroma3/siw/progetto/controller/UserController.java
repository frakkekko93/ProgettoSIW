package it.uniroma3.siw.progetto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController 
{
	/* Prende la home dell'utente */
	@RequestMapping(value = { "/userHome" }, method = RequestMethod.GET)
    public String home()
	{
		return "home";
    }
	
	/* Prende il profilo dell'utente */
	@RequestMapping(value = { "/user/profile" }, method = RequestMethod.GET)
    public String profile()
	{
		return "userProfile";
    }
}
