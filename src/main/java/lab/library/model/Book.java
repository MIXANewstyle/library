package lab.library.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Добавьте название книги")
    private String title;

    @NotEmpty(message = "Добавьте автора книги")
    private String author;

    @NotEmpty(message = "Добавьте описание книги")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Добавьте дату написания книги")
    @Column(name = "writing_date")
    private LocalDate writingDate;

    @Min(value = 1, message = "Минимальное количество страниц 1")
    private int pages;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    public Book() {
    }

    public Book(int id, String title, String author, String description, LocalDate writingDate, int pages, User user,
                File file) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.writingDate = writingDate;
        this.pages = pages;
        this.user = user;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getWritingDate() {
        return writingDate;
    }

    public int getPages() {
        return pages;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWritingDate(LocalDate writingDate) {
        this.writingDate = writingDate;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId() && getTitle().equals(book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", writingDate=" + writingDate +
                ", pages=" + pages +
                ", user=" + user +
                ", file=" + file +
                '}';
    }
}
