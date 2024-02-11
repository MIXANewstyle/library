package lab.library.service;

import lab.library.model.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    Book create(String title, String author, LocalDate writingDate, int pages, boolean bookIsFree, int userId);

    Book getBookById(int id);

    Book getBookByTitle(String id);

    List<Book> getBooksByAuthor(String author);

    Book deleteBookById(int id);

    Book takeBookById(int bookId, int userId);
}
