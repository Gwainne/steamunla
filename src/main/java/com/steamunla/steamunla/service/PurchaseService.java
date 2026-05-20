package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Purchase;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.PurchaseRepository;
import com.steamunla.steamunla.service.WishlistService;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private WishlistService wishlistService;

    /**
     * Verifica si un usuario ya ha comprado un juego específico
     * @param user el usuario
     * @param game el juego
     * @return true si el usuario ya posee el juego, false de lo contrario
     */
    public boolean hasUserAlreadyBoughtGame(User user, Game game) {
        return purchaseRepository.findByUserAndGame(user, game).isPresent();
    }

    /**
     * Realiza una compra de un juego
     * @param user el usuario que compra
     * @param game el juego a comprar
     * @param paymentMethod el método de pago
     * @return el objeto Purchase creado
     * @throws RuntimeException si el usuario ya ha comprado este juego
     */
    public Purchase buyGame(User user, Game game, String paymentMethod) {
        
        // Verificar si el usuario ya compró este juego
        if (hasUserAlreadyBoughtGame(user, game)) {
            throw new RuntimeException("Ya has comprado este juego");
        }

        Purchase purchase = new Purchase();

        purchase.setUser(user);
        purchase.setGame(game);
        purchase.setAmountPaid(game.getPrice());
        purchase.setPaymentMethod(paymentMethod);
        purchase.setStatus("COMPLETED");
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase saved = purchaseRepository.save(purchase);

        libraryService.addGameToLibrary(user, game);
        wishlistService.removeGameFromWishlist(user, game);

        return saved;
    }

    /**
     * Obtiene todas las compras realizadas por un usuario
     * @param user el usuario
     * @return lista de compras del usuario
     */
    public List<Purchase> getGamesPurchasedByUser(User user) {
        return purchaseRepository.findByUser(user);
    }
}