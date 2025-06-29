package it.uniroma3.siwBooks.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwBooks.model.Libro;
import it.uniroma3.siwBooks.model.Recensione;
import it.uniroma3.siwBooks.repository.LibroRepository;
import jakarta.transaction.Transactional;

@Service
public class LibroService {
	 @Autowired
	    private LibroRepository libroRepository;

	    public void save(Libro libro) {
	         this.libroRepository.save(libro);    //metodo per salvare un libro
	    }

	    public List<Libro> findAll() {
	        return (List<Libro>) this.libroRepository.findAll();  //metodo per ottenere una lista di tutti i libri
	    }

		public Libro findById(Long libroId) {
			return this.libroRepository.findById(libroId).get();   //metodo per trovare un libro ed i suoi dettagli a partire dall'Id
		}
		
		public void cancellaAutoreDaLibro(Long libroId, Long autoreId) {
			   this.libroRepository.cancellaAutoreDaLibro(libroId, autoreId);			
		}

		public void aggiungiAutoreALibro(Long libroId, Long autoreId) {
			this.libroRepository.aggiungiAutoreALibro(libroId, autoreId);
			
		}
		
		@Transactional
		public void delete(Libro libro) {
		    // Scollega recensioni
		    if (libro.getRecensioni() != null) {
		        for (Recensione r : libro.getRecensioni()) {
		            r.setLibro(null);
		        }
		        libro.getRecensioni().clear(); // rimuove dal lato del libro
		    }

		    // Scollega autore
		    if (libro.getAutori() != null) {
		        libro.setAutori(null);
		    }

		    // Ora puoi eliminare
		    libroRepository.delete(libro);
		}

}
