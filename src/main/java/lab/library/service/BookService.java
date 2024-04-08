package lab.library.service;

import lab.library.model.Book;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.FileDto;

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

    Optional<BookDto> takeBookById(int bookId, int userId);

    Optional<BookDto> putBookById(int bookId, int userId);


}
