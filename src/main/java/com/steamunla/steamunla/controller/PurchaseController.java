package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.PurchaseService;
import com.steamunla.steamunla.service.LibraryService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private GameService gameService;

    @Autowired
    private LibraryService libraryService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping("/checkout/{gameId}")
    public String showCheckout(@PathVariable Long gameId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        // Verificar si el juego sigue en la biblioteca del usuario
        if (libraryService.isGameInLibrary(user, game)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ya tienes este juego en tu biblioteca. Accede a ella para jugarlo.");
            return "redirect:/games/" + gameId;
        }

        model.addAttribute("game", game);
        model.addAttribute("user", user);

        return "purchases/checkout";
    }

    @PostMapping("/confirm")
    public String buyGame(@RequestParam Long gameId,
                          @RequestParam String paymentMethod,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        try {
            purchaseService.buyGame(user, game, paymentMethod);

            redirectAttributes.addFlashAttribute("game", game);
            redirectAttributes.addFlashAttribute("paymentMethod", paymentMethod);

            return "redirect:/purchases/success";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/games/" + gameId;
        }
    }

    @GetMapping("/success")
    public String showSuccess(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "purchases/purchase-success";
    }
}