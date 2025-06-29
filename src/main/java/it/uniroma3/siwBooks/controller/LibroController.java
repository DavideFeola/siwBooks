package it.uniroma3.siwBooks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwBooks.comparator.ComparatoreLibri;
import it.uniroma3.siwBooks.model.Autore;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.service.AutoreService;
import it.uniroma3.siwBooks.service.LibroService;

@Controller
public class LibroController {

  @Autowired
  private LibroService libroService;	
  
  @Autowired
  private AutoreService autoreService;	
	
	
  @GetMapping("/libri")
    public String showLibri(Model model) {
	model.addAttribute("libri", this.libroService.findAll());
	return "libri";	
   }  
  
  @GetMapping("/libro/{id}")
  public String getLibro(@PathVariable("id")Long id, Model model) {
 	 model.addAttribute("libro", this.libroService.findById(id));
 	 return "libro.html"; 
  }
  
  @GetMapping("/amministratore/gestioneDati")
  public String showDati(@RequestParam("username") String username,
                         @RequestParam(value = "titolo", required = false) String titolo,
                         Model model) {

      model.addAttribute("username", username);

      // Controllo autenticazione
      String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();
      if (!username.equals(usernameAutenticato)) {
          return "erroreAutorizzazione";
      }

      List<Libro> libri = new ArrayList<>(libroService.findAll());
      libri.sort(new ComparatoreLibri());
      List<Autore> autori = new ArrayList<>(autoreService.findAll());

      if (titolo != null && !titolo.trim().isEmpty()) {
          List<Libro> libriFiltrati = new ArrayList<>();
          List<Autore> autoriFiltrati = new ArrayList<>();

          for (Libro libro : libri) {
              if (libro.getTitolo().equalsIgnoreCase(titolo)) {
                  libriFiltrati.add(libro);
              }
          }

          for (Autore autore : autori) {
              if (autore.getCognome().equalsIgnoreCase(titolo)) {
                  autoriFiltrati.add(autore);
              }
          }

          if (libriFiltrati.isEmpty() && autoriFiltrati.isEmpty()) {
              model.addAttribute("errore", "Nessun libro o autore trovato con questo valore!");
          }

          model.addAttribute("libri", libriFiltrati);
          model.addAttribute("autori", autoriFiltrati);

      } else {
          // Nessuna ricerca: mostra tutto
          model.addAttribute("libri", libri);
          model.addAttribute("autori", autori);
      }

      return "gestisciDati";
  }

  
  
  @GetMapping("/amministratore/eliminaLibro/{id}")
  public String eliminaLibro(@RequestParam("username") String username,@PathVariable Long id,  Model model) {
	                    
      model.addAttribute("username", username);
      
      // Ricava username dell'utente attualmente autenticato e lo compara a quello passato
      
      String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

      if (!username.equals(usernameAutenticato)) {
          return "erroreAutorizzazione"; //erroreAutorizzazione
      }
      
      Libro libroDaEliminare=this.libroService.findById(id);
      libroService.delete(libroDaEliminare);
      
      return "redirect:/amministratore/gestioneDati?username=" + username;
 }  
  
  
 
  

}
	    	

