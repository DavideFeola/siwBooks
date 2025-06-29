package it.uniroma3.siwBooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.model.Recensione;
import it.uniroma3.siwBooks.model.Utente;
import it.uniroma3.siwBooks.service.CredenzialiService;
import it.uniroma3.siwBooks.service.RecensioneService;
import it.uniroma3.siwBooks.service.UtenteService;

@Controller
public class ProfiloController {
	@Autowired
    private UtenteService utenteService;
	
	@Autowired
	private RecensioneService recensioneService;
	
	@Autowired
	private CredenzialiService credenzialiService;
	

    @GetMapping("/profilo")
    public String profilo(Model model) {
        // Recupera l'autenticazione corrente
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Recupera l'oggetto Utente associato allo username
        Utente utente = utenteService.findByCredenzialiUsername(username);

        // Aggiunge l'utente al model per Thymeleaf
        model.addAttribute("user", utente);

        return "profilo.html";
    }
    
    //modificare con controllo su username
    
    @GetMapping("/profilo/leMieRecensioni")
    public String visualizzaRecensioni(@RequestParam("username") String username,  Model model) {
    	 model.addAttribute("username", username);
         List<Recensione> recensioni = recensioneService.findAll();      //trovo tutte le recensioni, dovrò poi vedere solo le mie
         Long utenteId = credenzialiService.findIdByUsername(username);  //trovo l'id del mio oggetto utente
         
         // Ricava l'username dell'utente attualmente autenticato
         String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

         // Verifica se l'utente sta cercando di accedere a un altro profilo       
         if (!username.equals(usernameAutenticato)) {         
             return "erroreAutorizzazione";                   //errore_autorizzazione
         }
         
         // creo una lista che contenga le mie recensioni da stampare
         List<Recensione> mieRecensioni= new ArrayList<>();
         
         for (Recensione recensione : recensioni) {
             if (Objects.equals(recensione.getMittenteId(), utenteId)) {
            	 Libro libro=recensione.getLibro();
            	 mieRecensioni.add(recensione);               //nel caso fosse mia la aggiungo alla collezione
             }
         }
         model.addAttribute("recensioni", mieRecensioni);
         return "mieRecensioni";
      
     }
    
    @GetMapping("/profilo/leMieRecensioni/cancellaRecensione/{id}")
    public String eliminaVoto(@RequestParam("username") String username, @PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("username", username);
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            return "erroreAutorizzazione";                 //erroreAutorizzazione.html
        }
        Recensione recensione=this.recensioneService.findById(id);
        this.recensioneService.delete(recensione);      
        redirectAttributes.addFlashAttribute("recensioneEliminata", "Recensione eliminata con successo.");
        return "redirect:/profilo/leMieRecensioni?username=" + username;

    }
    
     
}
