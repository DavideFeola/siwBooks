package it.uniroma3.siwBooks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Libro {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String titolo;
	  
	    @Max(2025)
	    @Min(1900)
	    @NotNull
	    private Integer anno;
	    
	    @ManyToMany
	    private List<Autore> autori; //qui il mapped by lo metto nella classe autore
	    
	    @OneToMany(mappedBy = "libro", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Recensione> recensioni;
	    
	    @OneToMany(mappedBy = "libro", cascade=CascadeType.ALL)
	    private List<Immagine> immagini=new ArrayList<>();

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Libro)) return false;
	        Libro libro = (Libro) o;
	        return this.id != null && this.id.equals(libro.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public Integer getAnno() {
			return anno;
		}

		public void setAnno(Integer anno) {
			this.anno = anno;
		}

		public List<Autore> getAutori() {
			return autori;
		}

		public void setAutori(List<Autore> autori) {
			this.autori = autori;
		}

		public List<Recensione> getRecensioni() {
			return recensioni;
		}

		public void setRecensioni(List<Recensione> recensioni) {
			this.recensioni = recensioni;
		}

		public List<Immagine> getImmagini() {
			return immagini;
		}

		public void setImmagini(List<Immagine> immagini) {
			this.immagini = immagini;
		}

		
}
