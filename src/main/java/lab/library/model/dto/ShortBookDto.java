package lab.library.model.dto;

import javax.validation.constraints.NotEmpty;

public class ShortBookDto {
    private int id;

    @NotEmpty(message = "Добавьте название книги")
    private String title;

    @NotEmpty(message = "Добавьте автора книги")
    private String author;

    public ShortBookDto(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ShortBookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
