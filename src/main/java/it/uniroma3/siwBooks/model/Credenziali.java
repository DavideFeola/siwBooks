package it.uniroma3.siwBooks.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credenziali {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)  //questo può essere un admin o un utente registrato qualunque
    private Ruolo ruolo;
    
    @OneToOne(mappedBy = "credenziali")
    private Utente utente;
    
    public Credenziali(String username, String password, Ruolo ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }
    
    public enum Ruolo {       //in questo sistema l amdmin e l unico che puo inserire libri e attori
        AMMINISTRATORE,
        UTENTE 
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, password, ruolo, username, utente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenziali other = (Credenziali) obj;
		return Objects.equals(id, other.id) && Objects.equals(password, other.password) && ruolo == other.ruolo
				&& Objects.equals(username, other.username) && Objects.equals(utente, other.utente);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Credenziali() {
	    // Necessario per JPA/Hibernate
	}

	
    
    
    
}
