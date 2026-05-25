package com.steamunla.steamunla.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.GameUpdate;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.GameUpdateService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/updates")
public class GameUpdateController {

    @Autowired
    private GameUpdateService gameUpdateService;

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    // Muestra el formulario para publicar una actualización
    @GetMapping("/new/{gameId}")
    public String showNewUpdateForm(@PathVariable Long gameId,
                                    Model model,
                                    HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/login";

        Game game = gameService.getGameById(gameId);
        List<GameUpdate> updates = gameUpdateService.getUpdatesByGame(game);

        model.addAttribute("game", game);
        model.addAttribute("updates", updates);
        model.addAttribute("user", user);

        return "updates/new";
    }

    // Procesa la publicación de una actualización
    @PostMapping("/create")
    public String createUpdate(@RequestParam Long gameId,
                               @RequestParam String version,
                               @RequestParam String patchNotes,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/login";

        Game game = gameService.getGameById(gameId);
        gameUpdateService.publishUpdate(game, version, patchNotes);

        redirectAttributes.addFlashAttribute("successMessage",
                "Actualización " + version + " publicada para " + game.getTitle());

        return "redirect:/updates/new/" + gameId;
    }
}