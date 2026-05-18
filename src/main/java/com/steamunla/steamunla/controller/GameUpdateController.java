
package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.GameUpdateService;

@Controller
@RequestMapping("/updates")
public class GameUpdateController {

    @Autowired
    private GameUpdateService updateService;

    @Autowired
    private GameService gameService;

    @GetMapping("/{gameId}")
    public String viewUpdates(@PathVariable Long gameId, Model model) {

        Game game = gameService.getGameById(gameId);

        model.addAttribute("game", game);
        model.addAttribute("updates", updateService.getUpdatesByGame(game));

        return "updates/index";
    }
}