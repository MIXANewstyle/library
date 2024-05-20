package lab.library.controller.user;

import lab.library.exceptions.BookNotFoundException;
import lab.library.exceptions.UserNotFoundException;
import lab.library.model.User;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.NewsDto;
import lab.library.service.BookService;
import lab.library.service.NewsService;
import lab.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final NewsService newsService;

    private final BookService bookService;

    public UserController(UserService userService, NewsService newsService, BookService bookService) {
        this.userService = userService;
        this.newsService = newsService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getNews(Model model) {
        model.addAttribute("listNews", newsService.getAllNews());
        return "user/news/news";
    }

    @GetMapping("/{id}")
    public String getNewsById(Model model, @PathVariable int id) {
        Optional<NewsDto> foundNews = newsService.getNewsById(id);
        if (foundNews.isEmpty()) {
            model.addAttribute("message", "Новость не найдена");
            return "errors/404";
        }
        model.addAttribute("news", foundNews.get());
        return "user/news/one";
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "user/book/books";
    }

    @GetMapping("/books/{bookId}")
    public String orderBookById(@PathVariable Integer bookId, Model model, Principal principal) {
        BookDto bookDto = bookService.getBookById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Данной книги не существует"));
        if (!userService.existsByLogin(principal.getName())) {
            throw new UserNotFoundException("Пользователя не существует");
        }
        if (bookDto.getUserId() != null) {
            model.addAttribute("message", "Данная книга уже забронирована");
            return "errors/404";
        }
        bookService.takeBookById(bookId, principal.getName());
        model.addAttribute("book", bookDto);
        return "success/201";
    }

    @GetMapping("/books/cancel/{bookId}")
    public String cancelOrderByBookId(@PathVariable Integer bookId, Model model, Principal principal) {
        BookDto bookDto = bookService.getBookById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Данной книги не существует"));
        User user = userService.findUserByLogin(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        if (bookDto.getUserId() != user.getId()) {
            model.addAttribute("message", "Данная книга не забронирована этим пользователем");
            return "errors/404";
        }
        bookService.putBookById(bookId, user.getId());
        model.addAttribute("book", bookDto);
        return "success/204";
    }

}
