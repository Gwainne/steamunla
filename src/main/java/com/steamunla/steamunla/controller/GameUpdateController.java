package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.GameUpdate;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.GameUpdateService;

@Controller
@RequestMapping("/updates")
public class GameUpdateController 
{
    @Autowired
    private GameUpdateService updateService;

    @Autowired
    private GameService gameService;

    // LISTAR
    @GetMapping("/{gameId}")
    public String viewUpdates(@PathVariable Long gameId, Model model) 
    {
        Game game = gameService.getGameById(gameId);

        model.addAttribute("game", game);
        model.addAttribute("updates", updateService.getUpdatesByGame(game));

        return "updates/index";
    }

    // FORM CREATE
    @GetMapping("/new/{gameId}")
    public String newUpdateForm(@PathVariable Long gameId, Model model) 
    {
        Game game = gameService.getGameById(gameId);

        model.addAttribute("game", game);
        model.addAttribute("update", new GameUpdate());

        return "updates/form";
    }

    // CREATE
    @PostMapping("/save/{gameId}")
    public String saveUpdate(@PathVariable Long gameId,
                             @RequestParam String version,
                             @RequestParam String patchNotes) 
    {
        Game game = gameService.getGameById(gameId);

        updateService.createUpdate(game, version, patchNotes);

        return "redirect:/updates/" + gameId;
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) 
    {
        GameUpdate update = updateService.getById(id);

        model.addAttribute("update", update);
        model.addAttribute("game", update.getGame());

        return "updates/edit";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String version,
                         @RequestParam String patchNotes) 
    {
        updateService.update(id, version, patchNotes);

        GameUpdate updated = updateService.getById(id);

        return "redirect:/updates/" + updated.getGame().getId();
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) 
    {
        GameUpdate update = updateService.getById(id);

        Long gameId = update.getGame().getId();

        updateService.delete(id);

        return "redirect:/updates/" + gameId;
    }
}