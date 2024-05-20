package lab.library.controller;

import lab.library.exceptions.UserNotFoundException;
import lab.library.model.User;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.ShortBookDto;
import lab.library.model.dto.UserDto;
import lab.library.service.BookService;
import lab.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final UserService userService;
    private final BookService bookService;

    public PersonalController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/{login}")
    public String getPersonalPage(Model model, @PathVariable String login, Principal principal) {
        UserDto user = userService.getUserDtoByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        if (!user.getLogin().equals(principal.getName())) {
            model.addAttribute("message", "Введен некорректный логин");
            return "errors/404";
        }
        List<ShortBookDto> books = bookService.getBooksByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("books", books);
        return "personalPage";
    }

    @PostMapping("/update")
    public String updateById(@Valid @ModelAttribute UserDto userDto, Model model,
                             @RequestParam MultipartFile multipartFile) {
        User user = userService.findUserById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        String oldLogin = user.getLogin();
        String oldPassword = user.getPassword();
        try {
            userService.editUserById(userDto, userDto.getId(),
                    new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
        if (!oldLogin.equals(userDto.getLogin()) || !oldPassword.equals(userDto.getPassword())) {
            return "redirect:/logout";
        }
        return "redirect:/personal/" + userDto.getLogin();
    }
}
