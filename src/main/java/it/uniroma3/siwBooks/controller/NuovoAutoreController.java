package it.uniroma3.siwBooks.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.uniroma3.siwBooks.model.Autore;
import it.uniroma3.siwBooks.model.Immagine;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.service.AutoreService;
import it.uniroma3.siwBooks.service.LibroService;
import jakarta.validation.Valid;


@RequestMapping("/amministratore") //prima di ogni richiesta
@Controller
public class NuovoAutoreController {
	@Autowired
    private AutoreService autoreService;

    @Autowired
    private LibroService libroService; //momentaneamente non serve

    @GetMapping("/nuovoAutore")
    public String nuovoAutore(@RequestParam("username") String username, Model model) {
        // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            // Puoi mostrare una pagina di errore o reindirizzare
            return "erroreAutorizzazione"; 
        }
        model.addAttribute("autore", new Autore()); //creo un oggetto autore e restituisco il form
        return "nuovoAutore";
    }

    @PostMapping("/autoreAggiunto")                           //richiesta post-mapping
    public String autoreAggiunto(@Valid Autore autore,
                                 BindingResult bindingResult,
                                 @RequestParam("foto") MultipartFile foto,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        
        // Validazione manuale: l'immagine è obbligatoria
    	
        if (foto == null || foto.isEmpty()) {
            bindingResult.rejectValue("fotografia", "error.autore", "La fotografia è obbligatoria");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("autore", autore);
            return "nuovoAutore";
        }
            try {
                String nomeFile = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path path = Paths.get("uploads/images/" + nomeFile);
                Files.copy(foto.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);      //se già esiste rimpiazza

                Immagine immagine = new Immagine();                                                //creo oggetto l'immagine
                immagine.setNomeFile(nomeFile);                                                    //setto il nome_file
                immagine.setPath("/images/" + nomeFile);                                            //setto il percorso
                immagine.setAutore(autore);                                                        //setto autore

                autore.setFotografia(immagine);                                                    //setto fotografia
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errore", "Errore nel salvataggio dell'immagine.");
                return "redirect:/nuovoAutore";                                                    //errore generico
            }

        autoreService.save(autore);                                                                // salverà anche la fotografia grazie a CascadeType.ALL
        redirectAttributes.addFlashAttribute("autoreAggiunto", "Autore aggiunto con successo!");       
        return "redirect:/";
    }
    
    @GetMapping("/aggiungiAutoreALibro/{id}")
    public String mostraAutoriDisponibili(@RequestParam("username") String username, @PathVariable("id")Long id, Model model) {
    	
    	String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!username.equals(usernameAutenticato)) {
            return "erroreAutorizzazione"; 
        }
        Libro libro=this.libroService.findById(id);
        model.addAttribute("libro", libro);
        model.addAttribute("autoriLibro", libro.getAutori()); //sono quelli già dal libro
        List<Autore> tutti=(List<Autore>)this.autoreService.findAll(); //sono tutti
        tutti.removeAll(libro.getAutori()); //da tutti rimuovo quelli del libro
        model.addAttribute("disponibili", tutti); //stampo i rimanenti e li passo al modello per iterare
        return "aggiungiAutoreALibro.html";
    }
    

    @GetMapping("/aggiungiAutore/{libroId}/{autoreId}")
    public String aggiungiAutoreAdUnLibro(@PathVariable("libroId") Long libroId,
                                  @PathVariable("autoreId") Long autoreId,
                                  Model model) {
 	   
 	   String usernameCorrente = SecurityContextHolder.getContext().getAuthentication().getName();

        this.libroService.aggiungiAutoreALibro(libroId, autoreId);
        return "redirect:/amministratore/modificaLibro/"+ libroId+ "?username=" +usernameCorrente ;
    }
    
  
}
