package com.steamunla.steamunla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.service.LibraryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private GameRepository gameRepository;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping
    public String showLibrary(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        List<Library> library = libraryService.getLibraryByUser(user);

        long installedCount = library.stream()
                .filter(Library::isInstalled)
                .count();

        model.addAttribute("library", library);
        model.addAttribute("user", user);
        model.addAttribute("installedCount", installedCount);

        return "library/index";
    }

    @GetMapping("/install/{gameId}")
    public String installGame(@PathVariable Long gameId, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        libraryService.markAsInstalled(user, game);

        return "redirect:/library";
    }

    @GetMapping("/download/{gameId}")
    public ResponseEntity<Resource> downloadGame(@PathVariable Long gameId, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        return libraryService.downloadGameFile(user, game);
    }

    @GetMapping("/uninstall/{gameId}")
    public String uninstallGame(@PathVariable Long gameId, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        libraryService.uninstallGame(user, game);

        return "redirect:/library";
    }
}