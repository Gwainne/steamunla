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
import com.steamunla.steamunla.service.LibraryService;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Muestra la biblioteca del usuario
    @GetMapping
    public String showLibrary(Model model) {

        // TODO: reemplazar por usuario logueado cuando Lara termine el login
        // Principal principal → userService.findByUsername(principal.getName())
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("matiA");

        List<Library> library = libraryService.getLibraryByUser(mockUser);
        model.addAttribute("library", library);
        model.addAttribute("user", mockUser);

        return "library/index"; // apunta a templates/library/index.html
    }

    // Descarga e instala un juego
    @GetMapping("/download/{gameId}")
    public ResponseEntity<Resource> downloadGame(@PathVariable Long gameId) {

        // TODO: reemplazar por usuario logueado
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("matiA");

        // TODO: reemplazar por gameService.findById cuando Mati P termine
        Game mockGame = new Game();
        mockGame.setId(gameId);
        mockGame.setTitle("Juego " + gameId);
        mockGame.setDeveloperName("Desarrollador");

        return libraryService.downloadGameFile(mockUser, mockGame);
    }
}