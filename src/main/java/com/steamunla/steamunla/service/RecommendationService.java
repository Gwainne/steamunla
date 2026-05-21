package com.steamunla.steamunla.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Purchase;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.LibraryRepository;
import com.steamunla.steamunla.repository.PurchaseRepository;

@Service
public class RecommendationService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    public List<Game> getRecommendations(User user, int limit) {
        List<Purchase> purchases = purchaseRepository.findByUser(user);
        Set<Long> ownedIds = libraryRepository.findByUser(user).stream()
                .map(entry -> entry.getGame().getId())
                .collect(Collectors.toSet());
        Set<Long> purchasedIds = purchases.stream()
                .map(p -> p.getGame().getId())
                .collect(Collectors.toSet());
        ownedIds.addAll(purchasedIds);

        if (purchases.isEmpty() && ownedIds.isEmpty()) {
            return getTopRatedGames(user, limit);
        }

        List<String> myGenres = libraryRepository.findByUser(user).stream()
                .map(entry -> entry.getGame().getGenre())
                .filter(g -> g != null && !g.isBlank())
                .distinct()
                .collect(Collectors.toList());

        if (myGenres.isEmpty()) {
            return getTopRatedGames(user, limit);
        }

        List<Game> recommendations;
        if (ownedIds.isEmpty()) {
            recommendations = gameRepository.findByGenreInOrderByAverageRatingDesc(myGenres);
        } else {
            recommendations = gameRepository.findByGenreInAndIdNotInOrderByAverageRatingDesc(
                    myGenres, List.copyOf(ownedIds));
        }
        return recommendations.stream().limit(limit).collect(Collectors.toList());
    }

    public List<Game> getDiscoveryGames(User user, int limit) {
        Set<Long> ownedIds = libraryRepository.findByUser(user).stream()
                .map(entry -> entry.getGame().getId())
                .collect(Collectors.toSet());

        Set<Long> purchasedIds = purchaseRepository.findByUser(user).stream()
                .map(p -> p.getGame().getId())
                .collect(Collectors.toSet());
        ownedIds.addAll(purchasedIds);

        Set<String> myGenres = libraryRepository.findByUser(user).stream()
                .map(entry -> entry.getGame().getGenre())
                .filter(g -> g != null && !g.isBlank())
                .collect(Collectors.toSet());

        List<Game> allGames;
        if (ownedIds.isEmpty()) {
            allGames = gameRepository.findAllByOrderByAverageRatingDesc();
        } else {
            allGames = gameRepository.findByIdNotInOrderByAverageRatingDesc(List.copyOf(ownedIds));
        }

        return allGames.stream()
                .filter(g -> g.getGenre() != null && !myGenres.contains(g.getGenre()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<Game> getTopRatedGames(User user, int limit) {
        Set<Long> ownedIds = libraryRepository.findByUser(user).stream()
                .map(entry -> entry.getGame().getId())
                .collect(Collectors.toSet());

        Set<Long> purchasedIds = purchaseRepository.findByUser(user).stream()
                .map(p -> p.getGame().getId())
                .collect(Collectors.toSet());

        ownedIds.addAll(purchasedIds);

        if (ownedIds.isEmpty()) {
            return gameRepository.findAllByOrderByAverageRatingDesc().stream()
                    .limit(limit).collect(Collectors.toList());
        }

        return gameRepository.findByIdNotInOrderByAverageRatingDesc(List.copyOf(ownedIds))
                .stream().limit(limit).collect(Collectors.toList());
    }
}
