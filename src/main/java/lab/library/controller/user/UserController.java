package lab.library.controller.user;

import lab.library.service.BookService;
import lab.library.service.NewsService;
import lab.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final NewsService newsService;

    private final BookService bookService;

    public UserController(NewsService newsService, BookService bookService) {
        this.newsService = newsService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("listNews", newsService.getAllNews());
        return "user/homePage/homePage";
    }

    @GetMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "user/book/books";
    }


}
