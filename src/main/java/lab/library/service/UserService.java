package lab.library.service;
import lab.library.model.Book;
import lab.library.model.Role;
import lab.library.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> createUser(User user);

    Optional<User> deleteUserById(int id);

    Optional<User> editUserById(User user);

    Optional<Role> getUserRoleById(int id);

    Optional<User> findUserById(int id);

    Optional<User> findUserByLogin(String login);

    Optional<User> banUserById(int id);

    void addBook(Book book, int userId);
}
