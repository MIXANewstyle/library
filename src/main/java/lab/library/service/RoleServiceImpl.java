package lab.library.service;

import lab.library.model.Role;
import lab.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.getRoleByName(roleName);
    }

    @Override
    public Optional<Role> getRoleById(int roleId) {
        return roleRepository.findById(roleId);
    }
}
