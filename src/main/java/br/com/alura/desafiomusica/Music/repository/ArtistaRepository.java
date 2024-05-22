package br.com.alura.desafiomusica.Music.repository;

import br.com.alura.desafiomusica.Music.model.Artista;
import br.com.alura.desafiomusica.Music.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nome);
}
