package it.uniroma3.siwBooks.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome; //obbligatorio

    @NotBlank
    private String cognome; //obbligatorio
    
    @PastOrPresent
    private LocalDate dataNascita; //facolatativa
    
    @PastOrPresent
    private LocalDate dataMorte; //facoltativa
    
    private String nazione; //nazione di provenienza, facoltativa
    
    @OneToOne(mappedBy = "autore", cascade = CascadeType.ALL)
    private Immagine fotografia;          //facoltativa
      
    @ManyToMany(mappedBy="autori")
    private List<Libro> libri;


	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataMorte, dataNascita, fotografia, id, libri, nazione, nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataMorte, other.dataMorte)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(fotografia, other.fotografia)
				&& Objects.equals(id, other.id) && Objects.equals(libri, other.libri)
				&& Objects.equals(nazione, other.nazione) && Objects.equals(nome, other.nome);
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public LocalDate getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	public LocalDate getDataMorte() {
		return dataMorte;
	}


	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}


	public String getNazione() {
		return nazione;
	}


	public void setNazione(String nazione) {
		this.nazione = nazione;
	}


	public Immagine getFotografia() {
		return fotografia;
	}


	public void setFotografia(Immagine fotografia) {
		this.fotografia = fotografia;
	}


	public List<Libro> getLibri() {
		return libri;
	}


	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}


	
}
