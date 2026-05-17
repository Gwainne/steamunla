package com.steamunla.steamunla.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Purchase;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.PurchaseRepository;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private LibraryService libraryService;

    public Purchase buyGame(User user, Game game, String paymentMethod) {

        Purchase purchase = new Purchase();

        purchase.setUser(user);
        purchase.setGame(game);
        purchase.setAmountPaid(game.getPrice());
        purchase.setPaymentMethod(paymentMethod);
        purchase.setStatus("COMPLETED");
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase saved = purchaseRepository.save(purchase);

        libraryService.addGameToLibrary(user, game);

        return saved;
    }
}