package lab.library.service;

import lab.library.model.Book;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.ShortBookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(Book book, FileDto fileDto);

    Optional<BookDto> getBookById(Integer id);

    List<BookDto> getAllBooks();

    Optional<Book> deleteBookById(Integer id);

    Optional<Book> updateBookById(BookDto bookDto, Integer id, FileDto fileDto);

    Optional<BookDto> getBookById(int id);

    Optional<BookDto> getBookByTitle(String id);

    List<BookDto> getBooksByAuthor(String author);

    List<ShortBookDto> getBooksByUserId(Integer userId);

    Optional<BookDto> takeBookById(int bookId, String login);

    Optional<BookDto> putBookById(int bookId, int userId);

}
