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
import com.steamunla.steamunla.model.Benefit;

import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.LibraryRepository;
import com.steamunla.steamunla.repository.PromotionRepository;
import com.steamunla.steamunla.repository.UserRepository;
import com.steamunla.steamunla.repository.GameUpdateRepository;
import com.steamunla.steamunla.repository.BenefitRepository;

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

    @Autowired
    private BenefitRepository benefitRepository;

    @Override
    public void run(String... args) {

        // SOLO SI NO HAY JUEGOS
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

            // UPDATES GAME 1
            GameUpdate update1 = new GameUpdate();
            update1.setGame(game1);
            update1.setVersion("1.0.1");
            update1.setPatchNotes("Mejora de rendimiento y fixes de bugs.");
            update1.setReleaseDate(LocalDateTime.now());
            gameUpdateRepository.save(update1);

            GameUpdate update2 = new GameUpdate();
            update2.setGame(game1);
            update2.setVersion("1.0.2");
            update2.setPatchNotes("Balance de armas y matchmaking.");
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

            // UPDATE GAME 2
            GameUpdate update3 = new GameUpdate();
            update3.setGame(game2);
            update3.setVersion("1.0.1");
            update3.setPatchNotes("Corrección de bugs y estabilidad.");
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

            // =========================
            // 💸 PROMOTION
            // =========================
            Promotion promo1 = new Promotion();
            promo1.setDescription("Oferta de verano");
            promo1.setDiscountPercent(50.0);
            promo1.setActive(true);
            promo1.setStartDate(LocalDateTime.now());
            promo1.setEndDate(LocalDateTime.now().plusDays(7));
            promo1.setGame(game1);

            promotionRepository.save(promo1);

            // =========================
            // 🎁 BENEFITS
            // =========================
            Benefit benefit1 = new Benefit();
            benefit1.setTitle("Skins exclusivas");
            benefit1.setDescription("Acceso a skins especiales dentro del juego.");
            benefit1.setActive(true);
            benefit1.setGame(game1);

            benefitRepository.save(benefit1);

            Benefit benefit2 = new Benefit();
            benefit2.setTitle("XP Bonus");
            benefit2.setDescription("+20% experiencia en partidas.");
            benefit2.setActive(true);
            benefit2.setGame(game2);

            benefitRepository.save(benefit2);
        }
    }
}