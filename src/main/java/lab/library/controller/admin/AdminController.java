package lab.library.controller.admin;

import lab.library.model.Book;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.FileDto;
import lab.library.service.BookService;
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
import java.util.Optional;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book/create";
    }

    @PostMapping("/create")
    public String addBook(@Valid @ModelAttribute Book book, @RequestParam MultipartFile multipartFile, Model model) {
        try {
            bookService.createBook(book, new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            return "redirect:/admin";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "admin/errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getBookById(Model model, @PathVariable int id) {
        Optional<BookDto> foundBook = bookService.getBookById(id);
        if (foundBook.isEmpty()) {
            model.addAttribute("message", "Книга не найдена");
            return "admin/errors/404";
        }
        model.addAttribute("book", foundBook.get());
        return "admin/book/one";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/book/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBookById(Model model, @PathVariable int id) {
        Optional<Book> foundBook = bookService.deleteBookById(id);
        if (foundBook.isEmpty()) {
            model.addAttribute("message", "Книга не найдена");
            return "admin/errors/404";
        }
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateById(@Valid @ModelAttribute BookDto book, Model model, @RequestParam MultipartFile multipartFile) {
        try {
            Optional<Book> foundBook = bookService.updateBookById(book, book.getId(),
                    new FileDto(multipartFile.getOriginalFilename(), multipartFile.getBytes()));
            if (foundBook.isEmpty()) {
                model.addAttribute("message", "Книга не найдена");
                return "admin/errors/404";
            }
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "admin/errors/404";
        }
        return "redirect:/admin";
    }
}
