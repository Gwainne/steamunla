package com.steamunla.steamunla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndGame(User user, Game game);

    void deleteByUserAndGame(User user, Game game);
}
