package com.steamunla.steamunla.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.Game;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    // Trae todos los juegos de un usuario
    List<Library> findByUser(User user);

    // Verifica si un juego ya está en la biblioteca del usuario
    Optional<Library> findByUserAndGame(User user, Game game);

    // Verifica si un juego está instalado
    List<Library> findByUserAndInstalledTrue(User user);
}