package it.uniroma3.siwBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siwBooks.model.Credenziali;
import it.uniroma3.siwBooks.model.Utente;
import it.uniroma3.siwBooks.service.CredenzialiService;
import it.uniroma3.siwBooks.service.UtenteService;

@Controller
public class RegisterController {

    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(required = false) String nome,
                               @RequestParam(required = false) String cognome,
                               @RequestParam(required = false) String email,
                               Model model) {
    	
         if (username == null || username.trim().isEmpty()) {
    	        model.addAttribute("error", "Lo username non può essere vuoto.");
    	        return "register";
    	 }

        if (password == null || password.trim().isEmpty()) {
    	        model.addAttribute("error", "La password non può essere vuota.");
    	        return "register";
    	 }

        if (credenzialiService.existsByUsername(username)) {
            model.addAttribute("esistente", "Username già esistente");
            return "register";
        }

        try {
            String encodedPassword = passwordEncoder.encode(password);
            Credenziali credenziali = new Credenziali(username, encodedPassword, Credenziali.Ruolo.UTENTE); //ruolo di default utente
            credenzialiService.save(credenziali);

            Utente utente = new Utente();
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setCredenziali(credenziali);
            utenteService.save(utente);

            model.addAttribute("success", "Registrazione completata! Effettua il login.");
            return "login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore durante la registrazione. Riprova.");
            return "register";
        }
    }
}
