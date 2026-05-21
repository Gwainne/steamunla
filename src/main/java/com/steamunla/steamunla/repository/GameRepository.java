package com.steamunla.steamunla.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByOrderByCreatedAtDesc();

    List<Game> findByPublisher(User publisher);

    List<Game> findByGenreInAndIdNotInOrderByAverageRatingDesc(List<String> genres, List<Long> excludedIds);

    List<Game> findByGenreInOrderByAverageRatingDesc(List<String> genres);

    List<Game> findByIdNotInOrderByAverageRatingDesc(List<Long> excludedIds);

    List<Game> findAllByOrderByAverageRatingDesc();

    boolean existsByPublisher(User publisher);
}