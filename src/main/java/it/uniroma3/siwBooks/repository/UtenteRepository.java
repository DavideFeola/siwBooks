package it.uniroma3.siwBooks.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siwBooks.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente,Long> {

	Utente findByCredenzialiUsername(String username);
}
