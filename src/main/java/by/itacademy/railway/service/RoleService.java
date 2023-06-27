package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.role.RoleStringDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.RoleMapper;
import by.itacademy.railway.repository.RoleRepository;
import by.itacademy.railway.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private final UserService userService;
    private final RoleMapper roleMapper;

    @Transactional
    public Optional<RoleReadDto> create(@Valid RoleStringDto roleStringDto) {
        return Optional.ofNullable(
                roleMapper.toDto(
                        roleRepository.save(
                                roleMapper.toEntity(roleStringDto))));
    }

    @Transactional
    public boolean remove(@NotNull(message = "Role id can't be null") Integer id) {
        deleteAllUsersWhichLinkedAtRole(id);
        roleRepository.deleteById(id);
        return !roleRepository.existsById(id);
    }

    /**
     * Удаляет пользователей у которых эта роль
     *
     * @param id идентификационный номер роли
     */
    private void deleteAllUsersWhichLinkedAtRole(Integer id) {
        roleRepository.findById(id).ifPresent(role -> role.getUsers()
                .stream()
                .map(User::getId)
                .forEach(userService::remove));
    }

}
