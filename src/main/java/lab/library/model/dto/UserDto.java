package lab.library.model.dto;

import java.util.List;

public class UserDto {

    private Integer id;

    private String name;

    private String surname;

    private String login;

    private String password;

    private Integer fileId;

    private List<BookDto> bookDtoList;

    public UserDto(String name, String surname, String login, String password, Integer fileId) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.fileId = fileId;
    }

    public UserDto(int id, String name, String surname, String login, String password, Integer fileId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.fileId = fileId;
    }

    public UserDto(int id, String name, String surname, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public List<BookDto> getBookDtoList() {
        return bookDtoList;
    }

    public void setBookDtoList(List<BookDto> bookDtoList) {
        this.bookDtoList = bookDtoList;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fileId=" + fileId +
                '}';
    }
}
