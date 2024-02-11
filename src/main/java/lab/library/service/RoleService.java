package lab.library.service;

import lab.library.model.Role;

public interface RoleService {
    Role getRoleByName(String roleName);

    Role getRoleById(int roleId);
}
