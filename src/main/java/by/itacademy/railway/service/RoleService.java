package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleDto;
import by.itacademy.railway.mapper.RoleMapper;
import by.itacademy.railway.repository.RoleRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

// TODO: 03.06.2023 доделать log
@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public boolean create(@Valid RoleDto roleDto) {
        roleRepository.save(roleMapper.toEntity(roleDto));
        return roleRepository.existsByRole(roleDto.getRole());
    }

    @Transactional
    public boolean remove(@NotBlank(message = "Role name can't be empty") String roleName) {
        roleRepository.deleteByRole(roleName);
        return !roleRepository.existsByRole(roleName);
    }

}
