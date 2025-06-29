package it.uniroma3.siwBooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.model.Recensione;
import it.uniroma3.siwBooks.model.Utente;
import it.uniroma3.siwBooks.service.CredenzialiService;
import it.uniroma3.siwBooks.service.LibroService;
import it.uniroma3.siwBooks.service.RecensioneService;
import it.uniroma3.siwBooks.service.UtenteService;
import it.uniroma3.siwBooks.comparator.ComparatoreLibri;


@RequestMapping("/utente") //prima di ogni richiesta da utente
@Controller
public class UtenteController {
	
	 @Autowired
	  private LibroService libroService;	
	 
	 @Autowired
	  private CredenzialiService credenzialiService;	
	 
	 @Autowired
	  private RecensioneService recensioneService;
		
	 @Autowired
	  private UtenteService utenteService;
	 
		
	  @GetMapping("/recensione")
	    public String lasciaRecensione(@RequestParam(value = "username") String username, @RequestParam(value = "titolo", required = false) String titolo, Model model) {
		  
		    List<Libro> libri = new ArrayList<>(libroService.findAll());      //cerco tutti i libri e li metto in un array
	        libri.sort(new ComparatoreLibri());                             //stampati in ordine di anno
	        model.addAttribute("username", username);
	        
	        // Ricava username dell'utente attualmente autenticato e lo compara a quello passato
	        
	        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	        if (!username.equals(usernameAutenticato)) {
	            return "erroreAutorizzazione"; //erroreAutorizzazione
	        }
	        
	        if (titolo != null) {                                //se il titolo che inserisco non è null itero su array di tutti i libri
	            List<Libro> listagen = new ArrayList<>();
	            for (Libro libro : libri) {
	            	if (libro.getTitolo().equalsIgnoreCase(titolo)) {      //controllo che il titolo è uguale a quello da me inserito
	                    listagen.add(libro);                               //se coincide lo inserisco in una lista che e quella da generare
	                }
	            }
	            if (listagen.isEmpty()) {                        //se è vuota nessuno coincide, non ci sono libri con quel titolo
	                model.addAttribute("errore", "Nessun libro trovato con questo titolo!");
	                return "recensione";
	            }
	            model.addAttribute("libri", listagen);           //appariranno solo i libri nella lista
	            return "recensione";
	        }
	        model.addAttribute("libri", libri);                  //caso base senza ricerca
	        return "recensione";	  
	   }  
	  
	  
	  @GetMapping("/recensisciLibro/{id}")
	    public String recensisciLibro(@RequestParam("username") String username, @PathVariable("id")Long id, Model model) {
		    System.out.println("Sto entrando nel metodo per recensire libro con ID: " + id);
	        Libro libro = libroService.findById(id);
	        List<Libro> libri = libroService.findAll();
	        libri.sort(new ComparatoreLibri());   

	        // Ricava username dell'utente attualmente autenticato e lo compara a quello passato         
	        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	        if (!username.equals(usernameAutenticato)) {
	            return "erroreAutorizzazione";
	        }

	        // utente ha già recensito questo libro?
	        Long utenteId = credenzialiService.findIdByUsername(username); //prendo l'id dell'utente
	        List<Recensione> recensioni = recensioneService.findAll();     //trovo tutte le recensioni
	        for (Recensione r: recensioni) {
	            if (Objects.equals(r.getMittenteId(), utenteId) && r.getLibro().equals(libro)) {
	                model.addAttribute("recensioneEsistente", "Hai già recensito questo Libro! Elimina la recensione per eseguirne un'altra!");
	                model.addAttribute("libri", libri);
	                model.addAttribute("username", username);
	                return "recensione";   //nel caso vieni reindirizzato alla schermata di prima con tutti i libri
	            }
	        }

	        // mostra il form per inviare la recensione
	        model.addAttribute("libro", libro);
	        model.addAttribute("username", username);
	        return "inviaRecensione";
	    }
	  
	  @PostMapping("/inviaRecensione")
	    public String inviaRecensione(@RequestParam(value = "username") String username, @RequestParam("voto") int voto, @RequestParam("descrizione") String descrizione,  @RequestParam("libroId") Long libroId, Model model, RedirectAttributes redirectAttributes) {
	        List<Recensione> recensioni = recensioneService.findAll();               //potrebbero servirmi 
	        Utente mittente=utenteService.findByCredenzialiUsername(username);       //dallo username mio mi ricavo il mio oggetto utente e lo salvo il una variabile mittente
	        
	        // Ricava username di utente attualmente autenticato, per CONTROLLO
	        
	        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	        // Verifica se l'utente sta cercando di accedere a un altro profilo
	        
	        if (!username.equals(usernameAutenticato)) {
	            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html
	        }
	    
	        Recensione recensione = new Recensione();              //creo un nuovo oggetto recensione
	        recensione.setMittente(mittente);                      //mi sto settando come mittente della recensione
	        recensione.setLibro(libroService.findById(libroId));   //sto settando il libro dopo averlo correttamente trovato dall'id
	        recensione.setVoto(voto);                              //setto l'attributo voto
	        recensione.setDescrizione(descrizione);                //setto la descrizione
	        recensioneService.save(recensione);                    //salvo la recensione
	        List<Libro> libri = libroService.findAll();            //mi serve dunque comunque la lista ordinata di tutti i libri per stampare il messaggio di successo 
	        libri.sort(new ComparatoreLibri());
	        model.addAttribute("username", username);
	        model.addAttribute("libri", libri);
	        redirectAttributes.addFlashAttribute("recensioneInviata", "Recensione salvata con successo!");
	        return "redirect:/utente/recensione?username=" + username;
	    }
	  
	  
	  @GetMapping("vediRecensioniLibro/{id}")
	    public String showRecensioniLibro(@RequestParam("username") String username, @PathVariable("id")Long id, Model model) {	  
		  
	        // Ricava username dell'utente attualmente autenticato e lo compara a quello passato         
	        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

	        if (!username.equals(usernameAutenticato)) {
	            return "erroreAutorizzazione";
	        }
	        
	        Libro libro=this.libroService.findById(id);
	        List<Recensione> recensioniDelLibro= libro.getRecensioni();
	        
	        model.addAttribute("recensioni", recensioniDelLibro);
	        model.addAttribute("libro", libro);
	        model.addAttribute("username", username);
	        
            return "recensioniLibro";
	    }
	  
	
}
