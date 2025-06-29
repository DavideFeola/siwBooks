
--CREDENZIALI

INSERT INTO credenziali (username, password, ruolo) VALUES ('luca23', '$2a$10$nzhy39f6/RGj/6ho.uuFX.a8FrptPT8n4eRym9HHpJTbAASsBk7Zu', 'UTENTE');
INSERT INTO credenziali (username, password, ruolo) VALUES ('marioRossi', '$2a$10$ADbLeIfSX9KAQATJhaNvOOCoLjncnAiaEshDk8fSj3M8O7nDrxkHO', 'UTENTE');
INSERT INTO credenziali (username, password, ruolo) VALUES ('davidefeola', '$2a$10$re8ldQc1c25aTxujp6zcjOiXg4A9UgbN9PawkSbeFJ98bgTFmFYFy', 'UTENTE');
INSERT INTO credenziali (username, password, ruolo) VALUES ('silvio', '$2a$10$re8ldQc1c25aTxujp6zcjOiXg4A9UgbN9PawkSbeFJ98bgTFmFYFy', 'AMMINISTRATORE');

-- UTENTE

INSERT INTO utente (nome, cognome, email, credenziali_id) VALUES ('Luca', 'Bianchi', 'luca.b@gmail.com', 1);
INSERT INTO utente (nome, cognome, email, credenziali_id) VALUES ('Mario', 'Rossi', 'mario.Rossi@gmail.com', 2);
INSERT INTO utente (nome, cognome, email, credenziali_id) VALUES ('Davide', 'Feola', 'davide.feola@gmail.com', 3);
INSERT INTO utente (nome, cognome, email, credenziali_id) VALUES ('Silvio', 'Neri', 'silvio.Neri@gmail.com', 4);


--LIBRO

INSERT INTO libro (titolo, anno) VALUES ('THE OCEAN BEYOND', '1930');
INSERT INTO libro (titolo, anno) VALUES ('DUST & SHADOW', '1949');
INSERT INTO libro (titolo, anno) VALUES ('THE FINAL ENIGMA', '2014');
INSERT INTO libro (titolo, anno) VALUES ('THE CLOCKMAKERS SECRET', '1989');
INSERT INTO libro (titolo, anno) VALUES ('SHADOW CASTLE', '2021');
INSERT INTO libro (titolo, anno) VALUES ('LOST IN THE WOODS', '1940');
INSERT INTO libro (titolo, anno) VALUES ('OSSI DI SEPPIA', '1925');
INSERT INTO libro (titolo, anno) VALUES ('ENRICO IV', '1921');




--IMMAGINE

INSERT INTO immagine (nome_file, path, libro_id) VALUES ('oceano.png', '/images/oceano.png', 1);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('dust.png', '/images/dust.png', 2);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('final.png', '/images/final.png', 3);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('clockmakers.png', '/images/clockmakers.png', 4);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('shadow.png', '/images/shadow.png', 5);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('lost.png', '/images/lost.png', 6);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('ossi.jpg', '/images/ossi.jpg', 7);
INSERT INTO immagine (nome_file, path, libro_id) VALUES ('enrico.jpg', '/images/enrico.jpg', 8);



--AUTORE

INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazione) VALUES ('Emily', 'Harper', '1960-11-11', '2024-10-10', 'Stati Uniti');
INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazione) VALUES ('Oscar', 'Woods', '1910-09-07', '1990-10-11', 'Scozia');
INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazione) VALUES ('George', 'Orwell', '1903-10-10', '1950-08-09', 'Inghilterra');
INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazione) VALUES ('Luigi', 'Pirandello', '1867-06-24', '1936-12-10', 'Italia');
INSERT INTO autore (nome, cognome, data_nascita, data_morte, nazione) VALUES ('Eugenio', 'Montale', '1896-08-24', '1981-10-10', 'Italia');


--FOTOGRAFIA

INSERT INTO immagine (nome_file, path, autore_id) VALUES ('harper.png', '/images/harper.png', 1);
INSERT INTO immagine (nome_file, path, autore_id) VALUES ('woods.png', '/images/woods.png', 2);
INSERT INTO immagine (nome_file, path, autore_id) VALUES ('orwell.jpg', '/images/orwell.jpg', 3);
INSERT INTO immagine (nome_file, path, autore_id) VALUES ('pirandello.jpg', '/images/pirandello.jpg', 4);
INSERT INTO immagine (nome_file, path, autore_id) VALUES ('Montale.jpg', '/images/Montale.jpg', 5);


--LIBRO_AUTORI

INSERT INTO libro_autori (autori_id, libri_id) VALUES (3, 1);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (2, 1);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (3, 2);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (1, 3);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (3, 3);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (2, 4);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (1, 5);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (2, 6);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (4, 8);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (3, 6);
INSERT INTO libro_autori (autori_id, libri_id) VALUES (5, 7);


--RECENSIONE

INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (5, 8, 3, 'non mi stanco mai di leggerlo..');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (5, 2, 1, 'molto interessante.');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (2, 3, 3, 'potrebbe essere più specifico in alcuni capitoli.');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (5, 3, 2, 'ho letto commenti anche negativi, ma a me è piaciuto moltissimo!!');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (4, 8, 1, 'pirandello sa sempre stupirci.');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (5, 8, 2, 'non ho più parole sufficenti!!');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (3, 7, 1, 'Montale, che raccolta fantastica, peccato per la lunghezza.');
INSERT INTO recensione (voto, libro_id, mittente_id, descrizione) VALUES (4, 7, 2, 'Wow.');
