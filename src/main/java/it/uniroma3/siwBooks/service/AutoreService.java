package it.uniroma3.siwBooks.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siwBooks.model.Autore;
import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.repository.AutoreRepository;
import jakarta.transaction.Transactional;

@Service
public class AutoreService {
	@Autowired
    private AutoreRepository autoreRepository;
	
	 public void save(Autore autore) {                               //metodo per salvare un autore
	        this.autoreRepository.save(autore);
	    }

	    public List<Autore> findAll() {                              //metodo per avere tutti gli autori
	        return (List<Autore>) this.autoreRepository.findAll();
	    }
	    
	    public Autore findById(Long id) {
	        return this.autoreRepository.findById(id).orElse(null);   //trovo l'autore ed i suoi dettagli dall'Id
	    }

	    @Transactional
	    public void delete(Autore autoreDaEliminare) {
	        // Rimuove l'autore da tutti i libri associati
	        for (Libro libro : autoreDaEliminare.getLibri()) {
	            libro.getAutori().remove(autoreDaEliminare);
	        }
	        autoreRepository.delete(autoreDaEliminare);
	    }
}
