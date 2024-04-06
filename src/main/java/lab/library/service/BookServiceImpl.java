package lab.library.service;

import lab.library.model.Book;
import lab.library.model.File;
import lab.library.model.News;
import lab.library.model.User;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.FileDto;
import lab.library.repository.BookRepository;
import lab.library.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final UserService userService;
    private final BookRepository bookRepository;
    private final FileRepository fileRepository;

    private final FileService fileService;

    public BookServiceImpl(UserService userService, BookRepository bookRepository, FileRepository fileRepository, FileService fileService) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @Override
    public Book createBook(Book book, FileDto fileDto) {
        File file = fileService.save(fileDto);
        book.setFile(file);
        return bookRepository.save(book);
    }

    @Override
    public Optional<BookDto> getBookById(Integer id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }
        Book book = foundBook.get();
        return Optional.of(new BookDto(id, book.getTitle(), book.getAuthor(), book.getDescription(),
                book.getWritingDate(), book.getPages(), book.getUser().getId(), book.getFile().getId()));
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(),
                        book.getWritingDate(), book.getPages(), book.getUser().getId(), book.getFile().getId()))
                .toList();
    }

    @Override
    public Optional<Book> deleteBookById(Integer id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }
        bookRepository.deleteById(id);
        fileService.deleteById(foundBook.get().getFile().getId());
        return foundBook;
    }

    //TODO
    @Override
    @Transactional
    public Optional<Book> updateBookById(BookDto bookDto, Integer id, FileDto fileDto) {
        boolean isNewFileEmpty = fileDto.getContent().length == 0;
        if (isNewFileEmpty) {
            Optional<File> optFile = fileRepository.findById(bookDto.getFileId());

            return Optional.of(bookRepository.save(new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getDescription(),
                    bookDto.getWritingDate(), bookDto.getPages(), bookDto.getUser().getId(), bookDto.getFile().getId())));
        }
        newsDto.setCreated(LocalDateTime.now());
        int oldFileId = newsDto.getFileId();
        File file = fileService.save(fileDto);
        News updatedNews = new News(id, newsDto.getTitle(), newsDto.getShortDescription(),
                newsDto.getFullDescription(), LocalDateTime.now(), file);
        News save = newsRepository.save(updatedNews);
        fileService.deleteById(oldFileId);
        return Optional.of(save);
    }







    @Override
    public Optional<Book> createBook(Book book) {
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> getBookById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }
        return foundBook;
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        Optional<Book> foundBook = bookRepository.findBookByTitle(title);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }
        return foundBook;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Book> putBookById(int bookId, int userId) {
        Optional<Book> foundBook = bookRepository.findById(bookId);
        Optional<User> foundUser = userService.findUserById(userId);
        if (foundBook.isEmpty() || foundUser.isEmpty() || !foundUser.get().getBooks().contains(foundBook.get())) {
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
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalBook.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }
        Book book = optionalBook.get();
        User user = optionalUser.get();
        book.setUser(user);
        userService.addBook(book, userId);
        return Optional.of(book);
    }
}
