package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.LibraryRepository;


@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    // Trae todos los juegos de la biblioteca del usuario
    public List<Library> getLibraryByUser(User user) {
        return libraryRepository.findByUser(user);
    }

    // Agrega un juego a la biblioteca, lo llama PurchaseService cuando se completa una compra
    public Library addGameToLibrary(User user, Game game) {
        // Verifica que el juego no esté ya en la biblioteca
        Optional<Library> existing = libraryRepository.findByUserAndGame(user, game);
        if (existing.isPresent()) {
            return existing.get();
        }

        Library entry = new Library();
        entry.setUser(user);
        entry.setGame(game);
        entry.setInstalled(false);
        entry.setAddedAt(LocalDateTime.now());
        return libraryRepository.save(entry);
    }

    // Verifica si un juego está en la biblioteca del usuario
    public boolean isGameInLibrary(User user, Game game) {
        return libraryRepository.findByUserAndGame(user, game).isPresent();
    }

    // Marca un juego como instalado
    public Library markAsInstalled(User user, Game game) {
        Library entry = libraryRepository.findByUserAndGame(user, game)
                .orElseThrow(() -> new RuntimeException("El juego no está en la biblioteca"));

        entry.setInstalled(true);
        entry.setLastPlayed(LocalDateTime.now());
        return libraryRepository.save(entry);
    }

    public ResponseEntity<Resource> downloadGameFile(User user, Game game) {
    // Verifica que el juego esté en la biblioteca
    Library entry = libraryRepository.findByUserAndGame(user, game)
            .orElseThrow(() -> new RuntimeException("El juego no está en la biblioteca"));

    // Marca como instalado
    entry.setInstalled(true);
    entry.setLastPlayed(LocalDateTime.now());
    libraryRepository.save(entry);

    // Genera el archivo .txt
    String content = "=================================\n" +
                     "Juego: " + game.getTitle() + "\n" +
                     "Desarrollador: " + game.getDeveloperName() + "\n" +
                     "Versión: 1.0.0\n" +
                     "Instalación completada con éxito.\n" +
                     "=================================";

    byte[] bytes = content.getBytes();
    ByteArrayResource resource = new ByteArrayResource(bytes);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + game.getTitle() + ".txt\"")
            .contentType(MediaType.TEXT_PLAIN)
            .contentLength(bytes.length)
            .body(resource);
    }

    public Library uninstallGame(User user, Game game) {
    Library entry = libraryRepository.findByUserAndGame(user, game)
            .orElseThrow(() -> new RuntimeException("El juego no está en la biblioteca"));

    entry.setInstalled(false);
    return libraryRepository.save(entry);
    }


}