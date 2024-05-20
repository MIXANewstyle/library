package lab.library.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookDto {
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
    private Integer userId;
    private Integer fileId;

    public BookDto(int id, String title, String author, String description, LocalDate writingDate, int pages, Integer userId, Integer fileId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.writingDate = writingDate;
        this.pages = pages;
        this.userId = userId;
        this.fileId = fileId;
    }

    public BookDto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getWritingDate() {
        return writingDate;
    }

    public void setWritingDate(LocalDate writingDate) {
        this.writingDate = writingDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return getId() == bookDto.getId() && getPages() == bookDto.getPages() && Objects.equals(getTitle(), bookDto.getTitle()) && Objects.equals(getAuthor(), bookDto.getAuthor()) && Objects.equals(getDescription(), bookDto.getDescription()) && Objects.equals(getWritingDate(), bookDto.getWritingDate()) && Objects.equals(getUserId(), bookDto.getUserId()) && Objects.equals(getFileId(), bookDto.getFileId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAuthor(), getDescription(), getWritingDate(), getPages(), getUserId(), getFileId());
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", writingDate=" + writingDate +
                ", pages=" + pages +
                ", userId=" + userId +
                ", fileId=" + fileId +
                '}';
    }
}
