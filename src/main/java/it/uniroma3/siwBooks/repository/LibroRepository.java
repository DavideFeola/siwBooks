package it.uniroma3.siwBooks.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siwBooks.model.Libro;
import jakarta.transaction.Transactional;

public interface LibroRepository extends CrudRepository<Libro,Long>{

	@Modifying
    @Transactional
    @Query(value = "DELETE FROM libro_autori WHERE libri_id = :libroId AND autori_id = :autoreId", nativeQuery = true)
    void cancellaAutoreDaLibro(@Param("libroId") Long libroId, @Param("autoreId") Long autoreId);

	@Modifying
    @Transactional
    @Query(value = "INSERT INTO libro_autori (libri_id, autori_id) VALUES (:libroId, :autoreId)", nativeQuery = true)
    void aggiungiAutoreALibro(@Param("libroId") Long libroId, @Param("autoreId") Long autoreId);
}
