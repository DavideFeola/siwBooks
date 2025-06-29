package it.uniroma3.siwBooks.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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

import it.uniroma3.siwBooks.model.Immagine;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.service.AutoreService;
import it.uniroma3.siwBooks.service.LibroService;
import jakarta.validation.Valid;

@RequestMapping("/amministratore") //prima di ogni richiesta
@Controller
public class NuovoLibroController {
	@Autowired
    private AutoreService autoreService; //momentanemante non serve

    @Autowired
    private LibroService libroService;

    @GetMapping("/nuovoLibro")
    public String nuovoLibro(@RequestParam("username") String username, Model model) {
        // Ricava l'username dell'utente attualmente autenticato
        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifica se l'utente sta cercando di accedere a un altro profilo
        if (!username.equals(usernameAutenticato)) {
            // Puoi mostrare una pagina di errore o reindirizzare
            return "erroreAutorizzazione"; // crea un template errore_autorizzazione.html, da aggiungere
        }
        model.addAttribute("libro", new Libro()); //creo oggetti
        return "nuovoLibro";
    }

    @PostMapping("/libroAggiunto")
    public String libroAggiunto(@Valid Libro libro,
                                BindingResult bindingResult,
                                @RequestParam("immagine") MultipartFile copertina,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (copertina == null || copertina.isEmpty()) {
            bindingResult.reject("copertinaObbligatoria", "La copertina è obbligatoria"); 
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("libro", libro);
            return "nuovoLibro";
        }

        try {
            String nomeFile = UUID.randomUUID().toString() + "_" + copertina.getOriginalFilename();
            Path path = Paths.get("uploads/images/" + nomeFile);
            Files.copy(copertina.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Immagine immagine = new Immagine();
            immagine.setNomeFile(nomeFile);
            immagine.setPath("/images/" + nomeFile);
            immagine.setLibro(libro);  // associa l'immagine al libro

            libro.getImmagini().add(immagine);  // aggiungi l'immagine alla lista
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errore", "Errore nel salvataggio della copertina.");
            return "redirect:/nuovoLibro";
        }

        libroService.save(libro);
        redirectAttributes.addFlashAttribute("libroAggiunto", "Libro aggiunto con successo!");
        return "redirect:/";
    }

    
    @GetMapping("/aggiornaLibri")
    public String showLibri(@RequestParam("username") String username, Model model) {
    	
    	 String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

         if (!username.equals(usernameAutenticato)) {
             return "erroreAutorizzazione"; 
         }
    	model.addAttribute("libri", this.libroService.findAll());
    	return "aggiornaLibri";	
       }  
   
   @GetMapping("/modificaLibro/{id}")   
   public String modificaLibro(@RequestParam("username") String username, @PathVariable("id")Long id, Model model) {
	   
	   String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();

       if (!username.equals(usernameAutenticato)) {
           return "erroreAutorizzazione"; 
       }
       model.addAttribute("libro", this.libroService.findById(id));
       return "modificaLibro.html"; 
     }
   
   @GetMapping("/rimuoviAutoreDaLibro/{libroId}/{autoreId}")
   public String cancellaAutore(@PathVariable("libroId") Long libroId,
                                 @PathVariable("autoreId") Long autoreId,
                                 Model model) {
	   
	   String usernameCorrente = SecurityContextHolder.getContext().getAuthentication().getName();

       this.libroService.cancellaAutoreDaLibro(libroId, autoreId);
       return "redirect:/amministratore/modificaLibro/"+ libroId+ "?username=" +usernameCorrente ;
   }
    
}
