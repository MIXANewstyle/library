package lab.library.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    @Column(name = "writing_date")
    private LocalDate writingDate;
    private int pages;
    @Column(name = "book_is_free")
    private boolean bookIsFree;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;
}
