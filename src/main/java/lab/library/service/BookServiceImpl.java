package lab.library.service;

import lab.library.model.Book;
import lab.library.model.User;
import lab.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private final UserService userService;
    private final BookRepository bookRepository;

    public BookServiceImpl(UserService userService, BookRepository bookRepository){
        this.userService = userService;
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> createBook(Book book) {
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> getBookById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isEmpty()){
            return Optional.empty();
        }
        return foundBook;
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        Optional<Book> foundBook = bookRepository.findBookByTitle(title);
        if (foundBook.isEmpty()){
            return Optional.empty();
        }
        return foundBook;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }
    @Override
    @Transactional
    public Optional<Book> putBookById(int bookId, int userId){
        Optional<Book> foundBook = bookRepository.findById(bookId);
        Optional<User> foundUser = userService.findUserById(userId);
        if (foundBook.isEmpty() || foundUser.isEmpty() || !foundUser.get().getBooks().contains(foundBook.get())){
            return Optional.empty();
        }
        Book currentBook = foundBook.get();
        currentBook.setUser(null);
        User currentUser = foundUser.get();
        currentUser.getBooks().remove(currentBook);
        return Optional.of(currentBook);
    }

    @Override
    @Transactional
    public Optional<Book> takeBookById(int bookId, int userId) {
        Optional<Book> optionalBook= bookRepository.findById(bookId);
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalBook.isEmpty() || optionalUser.isEmpty()){
            return Optional.empty();
        }
        Book book = optionalBook.get();
        User user = optionalUser.get();
        book.setUser(user);
        userService.addBook(book, userId);
        return Optional.of(book);
    }
}
