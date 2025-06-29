package it.uniroma3.siwBooks.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.model.Recensione;
import it.uniroma3.siwBooks.repository.RecensioneRepository;

@Service
public class RecensioneService {
	@Autowired
    private RecensioneRepository recensioneRepository;

    public List<Recensione> findAll() {
        return (List<Recensione>) this.recensioneRepository.findAll();   //metodo per trovare tutte le recensioni
    }

    public void save(Recensione recensione) {                            //metodo per salvare una recensione
        this.recensioneRepository.save(recensione);
    }

    public void delete(Recensione recensione) {                          //metodo per cancellare una recensione
        this.recensioneRepository.delete(recensione);
    }
    
    public Recensione findById(Long recensioneId) {
		return this.recensioneRepository.findById(recensioneId).get();   
	}
	

}
