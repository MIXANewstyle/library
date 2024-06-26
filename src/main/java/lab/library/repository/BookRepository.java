package lab.library.repository;

import lab.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    List<Book> findAllByUserId(Integer userId);

}
