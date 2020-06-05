package it.uniroma3.siw.progetto.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController 
{
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home()
	{
		return "home";
    }
}
