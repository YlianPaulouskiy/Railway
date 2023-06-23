package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.role.RoleStringDto;
import by.itacademy.railway.mapper.RoleMapper;
import by.itacademy.railway.repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

// TODO: 03.06.2023 доделать log
@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public Optional<RoleReadDto> create(@Valid RoleStringDto roleStringDto) {
        return Optional.ofNullable(
                roleMapper.toDto(
                        roleRepository.save(
                                roleMapper.toEntity(roleStringDto))));
    }

    @Transactional
    public boolean remove(Integer id) {
        roleRepository.deleteById(id);
        return !roleRepository.existsById(id);
    }

}
