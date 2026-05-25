package com.steamunla.steamunla.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.Review;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.Wishlist;
import com.steamunla.steamunla.service.LibraryService;
import com.steamunla.steamunla.service.ReviewService;
import com.steamunla.steamunla.service.WishlistService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Library> library = libraryService.getLibraryByUser(user);
        List<Wishlist> wishlist = wishlistService.getWishlistByUser(user);

        // Últimos 3 juegos jugados (los que tienen lastPlayed)
        List<Library> recentGames = library.stream()
                .filter(l -> l.getLastPlayed() != null)
                .sorted((a, b) -> b.getLastPlayed().compareTo(a.getLastPlayed()))
                .limit(3)
                .toList();

        // Reseñas del usuario
        List<Review> reviews = reviewService.getReviewsByUser(user);

        // Stats
        long installedCount = library.stream().filter(Library::isInstalled).count();

        model.addAttribute("user", user);
        model.addAttribute("library", library);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("recentGames", recentGames);
        model.addAttribute("reviews", reviews);
        model.addAttribute("installedCount", installedCount);
        model.addAttribute("wishlistCount", wishlist.size());

        return "profile/index";
    }
}