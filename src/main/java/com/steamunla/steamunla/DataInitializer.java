package com.steamunla.steamunla;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.GameUpdate;

import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.LibraryRepository;
import com.steamunla.steamunla.repository.PromotionRepository;
import com.steamunla.steamunla.repository.UserRepository;
import com.steamunla.steamunla.repository.GameUpdateRepository;

@Component
public class DataInitializer implements CommandLineRunner {

@Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameUpdateRepository gameUpdateRepository;

    @Override
    public void run(String... args) {

        // ✅ CORRECTO: solo corre si no hay juegos
        if (gameRepository.count() == 0) {

            // =========================
            // 👤 USER
            // =========================
            User user = new User();
            user.setUsername("matiA");
            user.setEmail("matia@steamunla.com");
            user.setPassword("1234");
            user.setRole("USER");
            user.setActive(true);
            user.setCreatedAt(LocalDateTime.now());
            user = userRepository.save(user);

            // =========================
            // 🎮 GAME 1
            // =========================
            Game game1 = new Game();
            game1.setTitle("Counter Strike 2");
            game1.setDeveloperName("Valve");
            game1.setGenre("FPS");
            game1.setPrice(0.0);
            game1.setActive(true);
            game1.setCreatedAt(LocalDateTime.now());
            game1 = gameRepository.save(game1);

            // 🧩 GAME 1 UPDATES
            GameUpdate update1 = new GameUpdate();
            update1.setGame(game1);
            update1.setVersion("1.0.1");
            update1.setPatchNotes("Mejora de rendimiento, optimización de servidores y fixes de bugs.");
            update1.setReleaseDate(LocalDateTime.now());
            gameUpdateRepository.save(update1);

            GameUpdate update2 = new GameUpdate();
            update2.setGame(game1);
            update2.setVersion("1.0.2");
            update2.setPatchNotes("Balance de armas y ajustes en matchmaking.");
            update2.setReleaseDate(LocalDateTime.now());
            gameUpdateRepository.save(update2);

            // =========================
            // 🎮 GAME 2
            // =========================
            Game game2 = new Game();
            game2.setTitle("Elden Ring");
            game2.setDeveloperName("FromSoftware");
            game2.setGenre("RPG");
            game2.setPrice(59.99);
            game2.setActive(true);
            game2.setCreatedAt(LocalDateTime.now());
            game2 = gameRepository.save(game2);

            // 🧩 GAME 2 UPDATES
            GameUpdate update3 = new GameUpdate();
            update3.setGame(game2);
            update3.setVersion("1.0.1");
            update3.setPatchNotes("Corrección de bugs y mejoras de estabilidad.");
            update3.setReleaseDate(LocalDateTime.now());
            gameUpdateRepository.save(update3);

            // =========================
            // 📚 LIBRARY
            // =========================
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

            Promotion promo1 = new Promotion();
            promo1.setDescription("Oferta de verano");
            promo1.setDiscountPercent(50.0);
            promo1.setActive(true);
            promo1.setStartDate(LocalDateTime.now());
            promo1.setEndDate(LocalDateTime.now().plusDays(7));
            promo1.setGame(game1);

            promotionRepository.save(promo1);

        }
    }
}

