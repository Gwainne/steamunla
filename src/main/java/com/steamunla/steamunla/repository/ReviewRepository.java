package com.steamunla.steamunla.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Review;
import com.steamunla.steamunla.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGame(Game game);
    List<Review> findByUser(User user);
}