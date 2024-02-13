package lab.library.service;

import lab.library.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleByName(String roleName);

    Optional<Role> getRoleById(int roleId);

}
