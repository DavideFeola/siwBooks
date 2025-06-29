package it.uniroma3.siwBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siwBooks.model.Credenziali;
import it.uniroma3.siwBooks.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	@Autowired
    private CredenzialiRepository credenzialiRepository;

    public boolean existsByUsername(String username) {            //verifica dell'esistenza delle credenzaili a partire dallo username
        return credenzialiRepository.existsByUsername(username);
    }

    public void save(Credenziali credenziali) {                   //metodo per salvare delle credenziali    
        this.credenzialiRepository.save(credenziali);
    }

    public Long findIdByUsername(String username) {               //metodo per trovare l'id delle credenziali dallo username
        return this.credenzialiRepository.findIdByUsername(username);
    }
}
