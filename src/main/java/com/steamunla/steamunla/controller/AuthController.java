package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/games";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new User());

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {

        userService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {

        return "auth/login";
    }
    @PostMapping("/login")
public String loginUser(
        @RequestParam String username,
        @RequestParam String password,
        HttpSession session,
        Model model) {

    User user = userService.login(username, password);

    if (user == null) {
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "auth/login";
    }

    session.setAttribute("loggedUser", user);

    return "redirect:/games";
}

@GetMapping("/logout")
public String logout(HttpSession session) {

    session.invalidate();

    return "redirect:/login";
}

@GetMapping("/session-test")
@ResponseBody
public String testSession(HttpSession session) {
    User u = (User) session.getAttribute("loggedUser");
    return u != null ? "Usuario en sesión: " + u.getUsername() : "Sesión vacía";
}

}