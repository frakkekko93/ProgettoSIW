package it.uniroma3.siw.progetto.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * The MainController is a Spring Boot Controller to handle
 * the generic interactions with the home pages (e.g. index page), that do not refer to specific entities
 */
@Controller
@RequestMapping("/")
public class MainController {
	

	@GetMapping("index")
    public String index()
	{
		return "index";
    }
}
