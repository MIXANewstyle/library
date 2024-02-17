package lab.library.service;

import lab.library.model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> createBook(Book book);

    Optional<Book> getBookById(int id);

    Optional<Book> getBookByTitle(String id);

    List<Book> getBooksByAuthor(String author);
    Optional<Book> takeBookById(int bookId, int userId);
    Optional<Book> putBookById(int bookId, int userId);
}
