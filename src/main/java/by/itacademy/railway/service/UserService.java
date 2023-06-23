package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.mapper.UserMapper;
import by.itacademy.railway.repository.RoleRepository;
import by.itacademy.railway.repository.UserRepository;
import by.itacademy.railway.service.exception.RoleNotFoundException;
import by.itacademy.railway.service.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// TODO: 08.06.2023 доделать логирование переписать Dto
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserReadDto> findAll() {
        return userMapper.toListUserRoleDto(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> findById(@NotNull(message = "User id can't be null") Long id) {
        return userRepository.findById(id).map(userMapper::toUserReadDto);
    }

    @Transactional
    public Optional<UserReadDto> create(@Valid UserStringDto userStringDto) {
        return Optional.ofNullable(
                userMapper.toUserReadDto(
                        userRepository.save(
                                userMapper.toEntity(userStringDto)
                        )
                )
        );
    }

    @Transactional
    public boolean remove(@NotNull(message = "User id can't be null") Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Transactional
    public void updateRole(Long id, RoleReadDto roleReadDto) throws RoleNotFoundException, UserNotFoundException {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            var optionalRole = roleRepository.findById(roleReadDto.getId());
            if (optionalRole.isPresent()) {
                var user = optionalUser.get();
                var role = optionalRole.get();
                user.setRole(role);
                userRepository.save(user);
                roleRepository.save(role);
            } else {
                throw new RoleNotFoundException(roleReadDto.getName());
            }
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> login(@NotBlank(message = "Email can't be empty") String email,
                                       @NotBlank(message = "Password can't be empty") String password) {
        return userRepository.login(email, password).map(userMapper::toUserReadDto);
    }

}
