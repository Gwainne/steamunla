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

import com.steamunla.steamunla.model.Benefit;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Library;
import com.steamunla.steamunla.model.User;

import com.steamunla.steamunla.repository.BenefitRepository;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.UserRepository;

import com.steamunla.steamunla.service.LibraryService;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BenefitRepository benefitRepository;

    @GetMapping
    public String showLibrary(Model model) {

        User mockUser = userRepository.findByUsername("matiA")
                .orElseThrow(() -> new RuntimeException("Usuario mock no encontrado"));

        List<Library> library = libraryService.getLibraryByUser(mockUser);

        // Contar instalados
        long installedCount = library.stream()
                .filter(Library::isInstalled)
                .count();

        // Obtener beneficios activos
        List<Benefit> benefits = benefitRepository.findByActiveTrue();

        model.addAttribute("library", library);
        model.addAttribute("user", mockUser);
        model.addAttribute("installedCount", installedCount);
        model.addAttribute("benefits", benefits);

        return "library/index";
    }

    // Marca un juego como instalado
    @GetMapping("/install/{gameId}")
    public String installGame(@PathVariable Long gameId) {

        User mockUser = userRepository.findByUsername("matiA")
                .orElseThrow(() -> new RuntimeException("Usuario mock no encontrado"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        libraryService.markAsInstalled(mockUser, game);

        return "redirect:/library";
    }

    // Descarga el juego y lo marca como instalado
    @GetMapping("/download/{gameId}")
    public ResponseEntity<Resource> downloadGame(@PathVariable Long gameId) {

        User mockUser = userRepository.findByUsername("matiA")
                .orElseThrow(() -> new RuntimeException("Usuario mock no encontrado"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        return libraryService.downloadGameFile(mockUser, game);
    }

    @GetMapping("/uninstall/{gameId}")
    public String uninstallGame(@PathVariable Long gameId) {

        User mockUser = userRepository.findByUsername("matiA")
                .orElseThrow(() -> new RuntimeException("Usuario mock no encontrado"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        libraryService.uninstallGame(mockUser, game);

        return "redirect:/library";
    }
}