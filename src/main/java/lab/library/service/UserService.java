package lab.library.service;

import lab.library.model.Role;
import lab.library.model.User;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<User> createUser(User user);

    Optional<User> deleteUserById(int id);

    Optional<User> editUserById(UserDto userDto, int userId, FileDto fileDto);

    Optional<Role> getUserRoleById(int id);

    Optional<User> findUserById(int id);

    Optional<User> findUserByLogin(String login);

    Optional<UserDto> getUserDtoByLogin(String login);

    Boolean existsByLogin(String login);
}
