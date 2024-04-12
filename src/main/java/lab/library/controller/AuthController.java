package lab.library.controller;

import lab.library.model.User;
import lab.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "auth/registration";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        Optional<User> newUser = userService.createUser(user);
        if (newUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с данным логином уже существует.");
            return "auth/errors/404";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/login-error")
    public String getLoginError(Model model) {
        model.addAttribute("error", "Логин или пароль введены неверно");
        return "auth/login";
    }
}
