package lab.library.controller;

import lab.library.service.BookService;
import lab.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage() {
        return "homePage/homePage";
    }

    @GetMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "book/books";
    }
}
