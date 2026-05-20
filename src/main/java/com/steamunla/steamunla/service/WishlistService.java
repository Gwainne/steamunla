package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.Wishlist;
import com.steamunla.steamunla.repository.WishlistRepository;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> getWishlistByUser(User user) {
        return wishlistRepository.findByUser(user);
    }

    public boolean isGameInWishlist(User user, Game game) {
        return wishlistRepository.findByUserAndGame(user, game).isPresent();
    }

    public Wishlist addGameToWishlist(User user, Game game) {
        Optional<Wishlist> existing = wishlistRepository.findByUserAndGame(user, game);
        if (existing.isPresent()) {
            return existing.get();
        }

        Wishlist entry = new Wishlist();
        entry.setUser(user);
        entry.setGame(game);
        entry.setAddedAt(LocalDateTime.now());
        return wishlistRepository.save(entry);
    }

    public void removeGameFromWishlist(User user, Game game) {
        wishlistRepository.findByUserAndGame(user, game).ifPresent(wishlistRepository::delete);
    }
}
