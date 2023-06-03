package by.itacademy.railway.service;

import by.itacademy.railway.dto.RoleDto;
import by.itacademy.railway.mapper.RoleMapper;
import by.itacademy.railway.repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

// TODO: 03.06.2023 доделать log и valid
@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public boolean create(@Valid RoleDto roleDto) {
        roleRepository.save(roleMapper.toEntity(roleDto));
        return roleRepository.existsByRole(roleDto.getRole());
    }

    public boolean remove(@Valid RoleDto roleDto) {
        roleRepository.deleteByRole(roleDto.getRole());
        return !roleRepository.existsByRole(roleDto.getRole());
    }

}
