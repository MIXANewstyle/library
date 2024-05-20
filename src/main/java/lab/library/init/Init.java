package lab.library.init;


import lab.library.model.Role;
import lab.library.model.User;
import lab.library.repository.RoleRepository;
import lab.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class Init {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    private static final String ROLE_USER = "ROLE_USER";

    public Init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        Optional<Role> optAdmin = roleRepository.findByName(ROLE_ADMIN);
        if (optAdmin.isEmpty()) {
           optAdmin = Optional.of(roleRepository.save(new Role(ROLE_ADMIN)));
            userRepository.save(new User("Nikita", "Shmatkov", "admin",
                    passwordEncoder.encode("12345"), optAdmin.get()));
        }
        Optional<Role> optModerator = roleRepository.findByName(ROLE_MODERATOR);
        if (optModerator.isEmpty()) {
            optModerator = Optional.of(roleRepository.save(new Role(ROLE_MODERATOR)));
            userRepository.save(new User("Andrei", "Nikolaev", "moderator",
                    passwordEncoder.encode("12345"), optModerator.get()));
        }
        Optional<Role> optUser = roleRepository.findByName(ROLE_USER);
        if (optUser.isEmpty()) {
            optUser = Optional.of(roleRepository.save(new Role(ROLE_USER)));
            userRepository.save(new User("Pacan", "Chushpanov", "user",
                    passwordEncoder.encode("12345"), optUser.get()));
        }
    }

}

