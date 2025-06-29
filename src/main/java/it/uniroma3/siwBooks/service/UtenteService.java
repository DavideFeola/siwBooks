package it.uniroma3.siwBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwBooks.model.Utente;
import it.uniroma3.siwBooks.repository.UtenteRepository;

@Service
public class UtenteService {
	@Autowired
    private UtenteRepository utenteRepository;

    public void save(Utente utente) {                                //metodo per salvare un utente
        this.utenteRepository.save(utente);
    }

    public Utente findByCredenzialiUsername(String username) {       //metodo per avere un utente grazie all'username
        return this.utenteRepository.findByCredenzialiUsername(username);
    }
}
