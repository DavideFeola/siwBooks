package it.uniroma3.siwBooks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.model.Recensione;
import it.uniroma3.siwBooks.service.LibroService;

@Controller
public class ClassificaController {
	
	@Autowired
	private LibroService libroService;

	@GetMapping("/classifica") 
	public String visualizzaClassifica(Model model) {                         
		List<Libro> libri=this.libroService.findAll();                       //trova tutti i libri sul Sistema
		Map<Long, Float> mediaVoti=new HashMap<>();                         //mappa con libri+la loro singola media
		List<Libro> libriFiltrati=new ArrayList<>();
		
		for (Libro libro: libri) {                                           //for per iterare su tutti i libri
			/*per controllo*/
			System.out.println("Libro: " + libro.getTitolo());
			System.out.println("Recensioni: " + libro.getRecensioni().size());
			List<Recensione> recensioni=libro.getRecensioni();               //di ogni libro trovo tutte le recensioni
			int somma=0;                                                     //somma voti recensioni, da dividere per n recensioni
			for (Recensione recensione: recensioni) {                        //itero sulle recensioni
				somma+=recensione.getVoto();                                 //somma incrementata di ogni voto di recensione
			}
			
			/*se la somma e 0 non viene fatta la frazione*/
			float media=recensioni.isEmpty() ? 0 : (float) somma / recensioni.size();     //media singola calcolata come somma di prima diviso il numero di recensioni
			if(media>3){
				mediaVoti.put(libro.getId(), media);                                                  //inserisco libro e la media delle recensioni nella mappa se questa è alta
				libriFiltrati.add(libro);                                                             //nuova lista
				System.out.println("Media: " + media);                                                //stampa di controllo
			}                                      
			}
		model.addAttribute("libri", libriFiltrati);                                   //passo SOLO i libri che mi interessano
	    model.addAttribute("medie", mediaVoti);                               //passo SOLO le medie che mi interessano
	    return "classifica";
				
}
	

}


