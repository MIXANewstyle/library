package lab.library.service;

import lab.library.model.Role;
import lab.library.model.User;
import lab.library.repository.UserRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> createUser(String name, String surname, String login, String password) {
        Optional<User> userOptional = userRepository.getUserByLogin(login);
        if (userOptional.isPresent()){
            return Optional.empty();
        }
        User newUser = new User(name, surname, login, password);
        return null;
    }

    @Override
    public User deleteUserById(int id) {
        return null;
    }

    @Override
    public User editUserById(int id, String name, String surname, String login, String password) {
        return null;
    }

    @Override
    public Role getUserRoleById(int id) {
        return null;
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        return null;
    }

    @Override
    public User banUserById(int id) {
        return null;
    }
}
