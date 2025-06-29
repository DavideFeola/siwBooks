package it.uniroma3.siwBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwBooks.model.Autore;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.service.AutoreService;

@Controller
public class AutoreController {
	
	@Autowired
	private AutoreService autoreService;
	
	@GetMapping("/autore/{autoreId}/{libroId}")
	  public String getAutore(@PathVariable("libroId") Long libroId,
			  @PathVariable("autoreId") Long autoreId,
			  Model model) {
	 	 model.addAttribute("autore", this.autoreService.findById(autoreId));
	 	 model.addAttribute("libroId", libroId);
	 	 return "autore.html"; 
	  }
	
	@GetMapping("/amministratore/eliminaAutore/{id}")
	  public String eliminaLibro(@RequestParam("username") String username,@PathVariable Long id,  Model model) {
		                    
	      model.addAttribute("username", username);
	      
	      // Ricava username dell'utente attualmente autenticato e lo compara a quello passato
	      
	      String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	      if (!username.equals(usernameAutenticato)) {
	          return "erroreAutorizzazione"; //erroreAutorizzazione
	      }
	      
	      Autore autoreDaEliminare=this.autoreService.findById(id);
	      autoreService.delete(autoreDaEliminare);
	      
	      return "redirect:/amministratore/gestioneDati?username=" + username;
	 }  
}


