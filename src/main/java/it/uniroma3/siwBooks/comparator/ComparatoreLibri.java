package it.uniroma3.siwBooks.comparator;

import java.util.Comparator;

import it.uniroma3.siwBooks.model.Libro;

public class ComparatoreLibri implements Comparator<Libro> {
	@Override
    public int compare(Libro l1, Libro l2) {
		return Long.compare(l1.getAnno(), l2.getAnno());

    }
}
