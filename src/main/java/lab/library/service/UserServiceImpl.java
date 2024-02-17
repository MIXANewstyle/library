package lab.library.service;

import lab.library.model.Book;
import lab.library.model.Role;
import lab.library.model.User;
import lab.library.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> createUser(User user) {
        Optional<User> userOptional = userRepository.findUserByLogin(user.getLogin());
        if (userOptional.isPresent()){
            return Optional.empty();
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> deleteUserById(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()){
            return Optional.empty();
        }
        userRepository.deleteById(id);
        return foundUser;
    }

    @Override
    @Transactional
    public Optional<User> editUserById(User user) {
        Optional<User> foundUser = userRepository.findById(user.getId());
        if (foundUser.isEmpty()){
            return Optional.empty();
        }
        User editedUser = foundUser.get();
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setLogin(user.getLogin());
        editedUser.setPassword(user.getPassword());
        return Optional.of(editedUser);
    }

    @Override
    public Optional<Role> getUserRoleById(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(foundUser.get().getRole());
    }
    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    @Transactional
    public Optional<User> banUserById(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()){
            return Optional.empty();
        }
        foundUser.get().setBanned(true);
        return foundUser;
    }

    @Override
    @Transactional
    public void addBook(Book book, int userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> value.getBooks().add(book));
    }
}
