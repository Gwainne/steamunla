package com.steamunla.steamunla;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.LibraryRepository;
import com.steamunla.steamunla.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void run(String... args) {
        if (libraryRepository.count() == 0) {

            // Guarda el usuario primero en la BD
            User user = new User();
            user.setUsername("matiA");
            user.setEmail("matia@steamunla.com");
            user.setPassword("1234");
            user.setRole("USER");
            user.setActive(true);
            user.setCreatedAt(LocalDateTime.now());
            user = userRepository.save(user); // ahora tiene ID real

            // Guarda los juegos en la BD
            Game game1 = new Game();
            game1.setTitle("Counter Strike 2");
            game1.setDeveloperName("Valve");
            game1.setGenre("FPS");
            game1.setPrice(0.0);
            game1.setActive(true);
            game1.setCreatedAt(LocalDateTime.now());
            game1 = gameRepository.save(game1); // ahora tiene ID real

            Game game2 = new Game();
            game2.setTitle("Elden Ring");
            game2.setDeveloperName("FromSoftware");
            game2.setGenre("RPG");
            game2.setPrice(59.99);
            game2.setActive(true);
            game2.setCreatedAt(LocalDateTime.now());
            game2 = gameRepository.save(game2); // ahora tiene ID real

            // Ahora sí guarda la biblioteca con referencias reales
            Library entry1 = new Library();
            entry1.setUser(user);
            entry1.setGame(game1);
            entry1.setInstalled(true);
            entry1.setAddedAt(LocalDateTime.now());
            libraryRepository.save(entry1);

            Library entry2 = new Library();
            entry2.setUser(user);
            entry2.setGame(game2);
            entry2.setInstalled(false);
            entry2.setAddedAt(LocalDateTime.now());
            libraryRepository.save(entry2);
        }
    }
}