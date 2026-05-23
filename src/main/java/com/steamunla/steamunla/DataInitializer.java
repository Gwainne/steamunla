package com.steamunla.steamunla;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.LibraryRepository;
import com.steamunla.steamunla.repository.PromotionRepository;
import com.steamunla.steamunla.repository.UserRepository;
import com.steamunla.steamunla.service.PurchaseService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Override
    public void run(String... args) {
        if (libraryRepository.count() == 0) {

            // Usuario publisher (desarrollador)
            User publisher = new User();
            publisher.setUsername("publisher");
            publisher.setEmail("publisher@steamunla.com");
            publisher.setPassword("publisher");
            publisher.setRole("USER");
            publisher.setActive(true);
            publisher.setCreatedAt(LocalDateTime.now());
            publisher = userRepository.save(publisher);

            // Usuario de prueba normal
            User testUser = new User();
            testUser.setUsername("test");
            testUser.setEmail("test@steamunla.com");
            testUser.setPassword("test");
            testUser.setRole("USER");
            testUser.setActive(true);
            testUser.setCreatedAt(LocalDateTime.now());
            testUser = userRepository.save(testUser);

            // Juegos RPG
            Game eldenRing = new Game();
            eldenRing.setTitle("Elden Ring"); eldenRing.setDeveloperName("FromSoftware"); eldenRing.setGenre("RPG"); eldenRing.setPrice(59.99); eldenRing.setAverageRating(4.8); eldenRing.setReleaseDate(LocalDateTime.now().minusMonths(12));
            eldenRing.setPublisher(publisher); eldenRing.setActive(true); eldenRing.setCreatedAt(LocalDateTime.now()); eldenRing.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/header.jpg");
            eldenRing = gameRepository.save(eldenRing);

            Game darkSouls3 = new Game();
            darkSouls3.setTitle("Dark Souls 3"); darkSouls3.setDeveloperName("FromSoftware"); darkSouls3.setGenre("RPG"); darkSouls3.setPrice(29.99); darkSouls3.setAverageRating(4.7); darkSouls3.setReleaseDate(LocalDateTime.now().minusMonths(36));
            darkSouls3.setPublisher(publisher); darkSouls3.setActive(true); darkSouls3.setCreatedAt(LocalDateTime.now()); darkSouls3.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/374320/header.jpg");
            gameRepository.save(darkSouls3);

            Game witcher3 = new Game();
            witcher3.setTitle("The Witcher 3"); witcher3.setDeveloperName("CD Projekt"); witcher3.setGenre("RPG"); witcher3.setPrice(19.99); witcher3.setAverageRating(4.9); witcher3.setReleaseDate(LocalDateTime.now().minusMonths(30));
            witcher3.setPublisher(publisher); witcher3.setActive(true); witcher3.setCreatedAt(LocalDateTime.now()); witcher3.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/292030/header.jpg");
            gameRepository.save(witcher3);

            Game baldursGate3 = new Game();
            baldursGate3.setTitle("Baldur's Gate 3"); baldursGate3.setDeveloperName("Larian Studios"); baldursGate3.setGenre("RPG"); baldursGate3.setPrice(59.99); baldursGate3.setAverageRating(5.0); baldursGate3.setReleaseDate(LocalDateTime.now().minusMonths(6));
            baldursGate3.setPublisher(publisher); baldursGate3.setActive(true); baldursGate3.setCreatedAt(LocalDateTime.now()); baldursGate3.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/header.jpg");
            gameRepository.save(baldursGate3);

            Game cyberpunk2077 = new Game();
            cyberpunk2077.setTitle("Cyberpunk 2077"); cyberpunk2077.setDeveloperName("CD Projekt"); cyberpunk2077.setGenre("RPG"); cyberpunk2077.setPrice(39.99); cyberpunk2077.setAverageRating(4.2); cyberpunk2077.setReleaseDate(LocalDateTime.now().minusMonths(24));
            cyberpunk2077.setPublisher(publisher); cyberpunk2077.setActive(true); cyberpunk2077.setCreatedAt(LocalDateTime.now()); cyberpunk2077.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1091500/header.jpg");
            gameRepository.save(cyberpunk2077);

            // Juegos FPS
            Game cs2 = new Game();
            cs2.setTitle("Counter Strike 2"); cs2.setDeveloperName("Valve"); cs2.setGenre("FPS"); cs2.setPrice(0.0); cs2.setAverageRating(4.1); cs2.setReleaseDate(LocalDateTime.now().minusMonths(2));
            cs2.setPublisher(publisher); cs2.setActive(true); cs2.setCreatedAt(LocalDateTime.now()); cs2.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/730/header.jpg");
            cs2 = gameRepository.save(cs2);

            Game valorant = new Game();
            valorant.setTitle("Valorant"); valorant.setDeveloperName("Riot Games"); valorant.setGenre("FPS"); valorant.setPrice(0.0); valorant.setAverageRating(3.9); valorant.setReleaseDate(LocalDateTime.now().minusMonths(24));
            valorant.setPublisher(publisher); valorant.setActive(true); valorant.setCreatedAt(LocalDateTime.now()); valorant.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2358720/header.jpg");
            gameRepository.save(valorant);

            Game apexLegends = new Game();
            apexLegends.setTitle("Apex Legends"); apexLegends.setDeveloperName("Respawn"); apexLegends.setGenre("FPS"); apexLegends.setPrice(0.0); apexLegends.setAverageRating(4.0); apexLegends.setReleaseDate(LocalDateTime.now().minusMonths(18));
            apexLegends.setPublisher(publisher); apexLegends.setActive(true); apexLegends.setCreatedAt(LocalDateTime.now()); apexLegends.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/header.jpg");
            gameRepository.save(apexLegends);

            Game overwatch2 = new Game();
            overwatch2.setTitle("Overwatch 2"); overwatch2.setDeveloperName("Blizzard"); overwatch2.setGenre("FPS"); overwatch2.setPrice(0.0); overwatch2.setAverageRating(3.5); overwatch2.setReleaseDate(LocalDateTime.now().minusMonths(3));
            overwatch2.setPublisher(publisher); overwatch2.setActive(true); overwatch2.setCreatedAt(LocalDateTime.now()); overwatch2.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2357570/header.jpg");
            gameRepository.save(overwatch2);

            Game codMW3 = new Game();
            codMW3.setTitle("Call of Duty MW3"); codMW3.setDeveloperName("Activision"); codMW3.setGenre("FPS"); codMW3.setPrice(69.99); codMW3.setAverageRating(3.8); codMW3.setReleaseDate(LocalDateTime.now().minusMonths(1));
            codMW3.setPublisher(publisher); codMW3.setActive(true); codMW3.setCreatedAt(LocalDateTime.now()); codMW3.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2519060/header.jpg");
            gameRepository.save(codMW3);

            // Juegos Strategy
            Game civ6 = new Game();
            civ6.setTitle("Civilization VI"); civ6.setDeveloperName("Firaxis"); civ6.setGenre("Strategy"); civ6.setPrice(29.99); civ6.setAverageRating(4.6); civ6.setReleaseDate(LocalDateTime.now().minusMonths(30));
            civ6.setPublisher(publisher); civ6.setActive(true); civ6.setCreatedAt(LocalDateTime.now()); civ6.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/289070/header.jpg");
            civ6 = gameRepository.save(civ6);

            Game aoe4 = new Game();
            aoe4.setTitle("Age of Empires IV"); aoe4.setDeveloperName("Relic"); aoe4.setGenre("Strategy"); aoe4.setPrice(49.99); aoe4.setAverageRating(4.4); aoe4.setReleaseDate(LocalDateTime.now().minusMonths(14));
            aoe4.setPublisher(publisher); aoe4.setActive(true); aoe4.setCreatedAt(LocalDateTime.now()); aoe4.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1466860/header.jpg");
            gameRepository.save(aoe4);

            Game sc2 = new Game();
            sc2.setTitle("StarCraft II"); sc2.setDeveloperName("Blizzard"); sc2.setGenre("Strategy"); sc2.setPrice(0.0); sc2.setAverageRating(4.5); sc2.setReleaseDate(LocalDateTime.now().minusMonths(36));
            sc2.setPublisher(publisher); sc2.setActive(true); sc2.setCreatedAt(LocalDateTime.now()); sc2.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2357570/header.jpg");
            gameRepository.save(sc2);

            // Juegos Adventure
            Game rdr2 = new Game();
            rdr2.setTitle("Red Dead Redemption 2"); rdr2.setDeveloperName("Rockstar"); rdr2.setGenre("Adventure"); rdr2.setPrice(39.99); rdr2.setAverageRating(4.9); rdr2.setReleaseDate(LocalDateTime.now().minusMonths(24));
            rdr2.setPublisher(publisher); rdr2.setActive(true); rdr2.setCreatedAt(LocalDateTime.now()); rdr2.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1174180/header.jpg");
            gameRepository.save(rdr2);

            Game tlou = new Game();
            tlou.setTitle("The Last of Us"); tlou.setDeveloperName("Naughty Dog"); tlou.setGenre("Adventure"); tlou.setPrice(49.99); tlou.setAverageRating(4.8); tlou.setReleaseDate(LocalDateTime.now().minusMonths(16));
            tlou.setPublisher(publisher); tlou.setActive(true); tlou.setCreatedAt(LocalDateTime.now()); tlou.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1888930/header.jpg");
            gameRepository.save(tlou);

            Game godOfWar = new Game();
            godOfWar.setTitle("God of War"); godOfWar.setDeveloperName("Santa Monica"); godOfWar.setGenre("Adventure"); godOfWar.setPrice(49.99); godOfWar.setAverageRating(4.9); godOfWar.setReleaseDate(LocalDateTime.now().minusMonths(9));
            godOfWar.setPublisher(publisher); godOfWar.setActive(true); godOfWar.setCreatedAt(LocalDateTime.now()); godOfWar.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1593500/header.jpg");
            gameRepository.save(godOfWar);

            // Juegos Sports
            Game fifa24 = new Game();
            fifa24.setTitle("FIFA 24"); fifa24.setDeveloperName("EA Sports"); fifa24.setGenre("Sports"); fifa24.setPrice(59.99); fifa24.setAverageRating(3.6); fifa24.setReleaseDate(LocalDateTime.now().minusMonths(5));
            fifa24.setPublisher(publisher); fifa24.setActive(true); fifa24.setCreatedAt(LocalDateTime.now()); fifa24.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2195250/header.jpg");
            gameRepository.save(fifa24);

            Game rocketLeague = new Game();
            rocketLeague.setTitle("Rocket League"); rocketLeague.setDeveloperName("Psyonix"); rocketLeague.setGenre("Sports"); rocketLeague.setPrice(0.0); rocketLeague.setAverageRating(4.3); rocketLeague.setReleaseDate(LocalDateTime.now().minusMonths(30));
            rocketLeague.setPublisher(publisher); rocketLeague.setActive(true); rocketLeague.setCreatedAt(LocalDateTime.now()); rocketLeague.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/252950/header.jpg");
            gameRepository.save(rocketLeague);

            Game nba2k24 = new Game();
            nba2k24.setTitle("NBA 2K24"); nba2k24.setDeveloperName("2K Sports"); nba2k24.setGenre("Sports"); nba2k24.setPrice(59.99); nba2k24.setAverageRating(3.7); nba2k24.setReleaseDate(LocalDateTime.now().minusMonths(4));
            nba2k24.setPublisher(publisher); nba2k24.setActive(true); nba2k24.setCreatedAt(LocalDateTime.now()); nba2k24.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2338770/header.jpg");
            gameRepository.save(nba2k24);

            // Juegos Horror
            Game re4 = new Game();
            re4.setTitle("Resident Evil 4"); re4.setDeveloperName("Capcom"); re4.setGenre("Horror"); re4.setPrice(39.99); re4.setAverageRating(4.7); re4.setReleaseDate(LocalDateTime.now().minusMonths(10));
            re4.setPublisher(publisher); re4.setActive(true); re4.setCreatedAt(LocalDateTime.now()); re4.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/2050650/header.jpg");
            gameRepository.save(re4);

            Game dbd = new Game();
            dbd.setTitle("Dead by Daylight"); dbd.setDeveloperName("Behaviour"); dbd.setGenre("Horror"); dbd.setPrice(19.99); dbd.setAverageRating(4.0); dbd.setReleaseDate(LocalDateTime.now().minusMonths(28));
            dbd.setPublisher(publisher); dbd.setActive(true); dbd.setCreatedAt(LocalDateTime.now()); dbd.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/381210/header.jpg");
            gameRepository.save(dbd);

            Game phasmophobia = new Game();
            phasmophobia.setTitle("Phasmophobia"); phasmophobia.setDeveloperName("Kinetic Games"); phasmophobia.setGenre("Horror"); phasmophobia.setPrice(13.99); phasmophobia.setAverageRating(4.5); phasmophobia.setReleaseDate(LocalDateTime.now().minusMonths(20));
            phasmophobia.setPublisher(publisher); phasmophobia.setActive(true); phasmophobia.setCreatedAt(LocalDateTime.now()); phasmophobia.setImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/739630/header.jpg");
            gameRepository.save(phasmophobia);

            // Biblioteca del usuario test usando PurchaseService
            // Así se crean tanto los registros en purchases como en library
            purchaseService.buyGame(testUser, cs2, "CREDIT_CARD");
            purchaseService.buyGame(testUser, eldenRing, "CREDIT_CARD");
            purchaseService.buyGame(testUser, civ6, "CREDIT_CARD");

            // Promociones activas
            Promotion p1 = new Promotion();
            p1.setGame(witcher3); p1.setDiscountPercent(50.0); p1.setStartDate(LocalDateTime.now().minusDays(1)); p1.setEndDate(LocalDateTime.now().plusDays(7)); p1.setActive(true); p1.setDescription("Oferta de temporada");
            promotionRepository.save(p1);

            Promotion p2 = new Promotion();
            p2.setGame(cyberpunk2077); p2.setDiscountPercent(30.0); p2.setStartDate(LocalDateTime.now().minusDays(1)); p2.setEndDate(LocalDateTime.now().plusDays(7)); p2.setActive(true); p2.setDescription("Descuento de lanzamiento");
            promotionRepository.save(p2);

            Promotion p3 = new Promotion();
            p3.setGame(fifa24); p3.setDiscountPercent(20.0); p3.setStartDate(LocalDateTime.now().minusDays(1)); p3.setEndDate(LocalDateTime.now().plusDays(7)); p3.setActive(true); p3.setDescription("Promo fin de semana");
            promotionRepository.save(p3);

            Promotion p4 = new Promotion();
            p4.setGame(dbd); p4.setDiscountPercent(25.0); p4.setStartDate(LocalDateTime.now().minusDays(1)); p4.setEndDate(LocalDateTime.now().plusDays(7)); p4.setActive(true); p4.setDescription("Halloween sale");
            promotionRepository.save(p4);
        }
    }
}