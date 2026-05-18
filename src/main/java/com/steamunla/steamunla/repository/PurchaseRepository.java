package com.steamunla.steamunla.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Purchase;
import com.steamunla.steamunla.model.User;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUser(User user);

    Optional<Purchase> findByUserAndGame(User user, Game game);
}