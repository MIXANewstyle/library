package lab.library.service;

import lab.library.model.Role;
import lab.library.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> createUser (String name, String surname, String login, String password);
    User deleteUserById (int id);
    User editUserById(int id, String name, String surname, String login, String password);
    Role getUserRoleById(int id);
    User findUserById(int id);
    User findUserByLogin(String login);
    User banUserById (int id);

    //suggest a book,
}
