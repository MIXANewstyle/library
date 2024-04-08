package lab.library.service;

import lab.library.model.Book;
import lab.library.model.File;
import lab.library.model.User;
import lab.library.model.dto.BookDto;
import lab.library.model.dto.FileDto;
import lab.library.repository.BookRepository;
import lab.library.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final UserService userService;
    private final BookRepository bookRepository;
    private final FileRepository fileRepository;

    private final FileService fileService;

    public BookServiceImpl(UserService userService, BookRepository bookRepository, FileRepository fileRepository,
                           FileService fileService) {
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
        return Optional.of(bookToBookDto(book));
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::bookToBookDto)
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

    @Override
    @Transactional
    public Optional<Book> updateBookById(BookDto bookDto, Integer id, FileDto fileDto) {
        Book oldBook = bookRepository.findById(bookDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Книга по указанному id не найдена"));
        User user = bookDto.getUserId() == null ? null : userService.findUserById(id).get();
        boolean isNewFileEmpty = fileDto.getContent().length == 0;
        if (isNewFileEmpty) {
            Optional<File> optFile = fileRepository.findById(bookDto.getFileId());
            setOldBookUpdatedFields(oldBook, bookDto, user, optFile.get());
            return Optional.of(bookRepository.save(oldBook));
        }
        int oldFileId = bookDto.getFileId();
        File file = fileService.save(fileDto);
        setOldBookUpdatedFields(oldBook, bookDto, user, file);
        Book save = bookRepository.save(oldBook);
        fileService.deleteById(oldFileId);
        return Optional.of(save);
    }

    @Override
    public Optional<BookDto> getBookById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.map(this::bookToBookDto);
    }

    @Override
    public Optional<BookDto> getBookByTitle(String title) {
        Optional<Book> foundBook = bookRepository.findBookByTitle(title);
        return foundBook.map(this::bookToBookDto);
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findBooksByAuthor(author);
        return books.stream()
                .map(this::bookToBookDto)
                .toList();
    }

    @Override
    @Transactional
    public Optional<BookDto> putBookById(int bookId, int userId) {
        Optional<Book> foundBook = bookRepository.findById(bookId);
        Optional<User> foundUser = userService.findUserById(userId);
        if (foundBook.isEmpty() || foundUser.isEmpty() || !foundUser.get().getBooks().contains(foundBook.get())) {
            return Optional.empty();
        }
        Book currentBook = foundBook.get();
        currentBook.setUser(null);
        User currentUser = foundUser.get();
        currentUser.getBooks().remove(currentBook);
        return Optional.of(bookToBookDto(currentBook));
    }

    @Override
    @Transactional
    public Optional<BookDto> takeBookById(int bookId, int userId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalBook.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }
        Book book = optionalBook.get();
        User user = optionalUser.get();
        book.setUser(user);
        userService.addBook(book, userId);
        return Optional.of(bookToBookDto(book));
    }

    private void setOldBookUpdatedFields(Book oldBook, BookDto bookUpdateDto, User user, File file) {
        oldBook.setTitle(bookUpdateDto.getTitle());
        oldBook.setAuthor(bookUpdateDto.getAuthor());
        oldBook.setDescription(bookUpdateDto.getDescription());
        oldBook.setWritingDate(bookUpdateDto.getWritingDate());
        oldBook.setPages(bookUpdateDto.getPages());
        oldBook.setUser(user);
        oldBook.setFile(file);
    }

    private BookDto bookToBookDto(Book book) {
        User user = book.getUser();
        Integer userId = user == null ? null : user.getId();
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getWritingDate(),
                book.getPages(), userId, book.getFile().getId());
    }
}
