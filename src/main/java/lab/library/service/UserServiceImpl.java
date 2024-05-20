package lab.library.service;

import lab.library.exceptions.UserNotFoundException;
import lab.library.model.File;
import lab.library.model.Role;
import lab.library.model.User;
import lab.library.model.dto.FileDto;
import lab.library.model.dto.UserDto;
import lab.library.repository.RoleRepository;
import lab.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    @Override
    public Optional<User> createUser(User user) {
        Optional<User> userOptional = userRepository.findUserByLogin(user.getLogin());
        if (userOptional.isPresent()) {
            return Optional.empty();
        }
        user.setRole(roleRepository.findByName("ROLE_USER").get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> deleteUserById(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            return Optional.empty();
        }
        userRepository.deleteById(id);
        return foundUser;
    }

    @Override
    @Transactional
    public Optional<User> editUserById(UserDto userDto, int userId, FileDto fileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setLogin(userDto.getLogin());
        if (!user.getPassword().equals(userDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        boolean isNewFileEmpty = fileDto.getContent().length == 0;
        if (!isNewFileEmpty) {
            File file = fileService.save(fileDto);
            user.setFile(file);
            if (userDto.getFileId() != null) {
                int oldFileId = userDto.getFileId();
                fileService.deleteById(oldFileId);
            }
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<Role> getUserRoleById(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
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
    public Optional<UserDto> getUserDtoByLogin(String login) {
        Optional<User> optUser = userRepository.findUserByLogin(login);
        if (optUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optUser.get();
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getSurname(), user.getLogin(), user.getPassword());
        if (user.getFile() != null) {
            userDto.setFileId(user.getFile().getId());
        }
        return Optional.of(userDto);
    }

    @Override
    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}
