package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserRoleDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.UserMapper;
import by.itacademy.railway.repository.RoleRepository;
import by.itacademy.railway.repository.UserRepository;
import by.itacademy.railway.service.exception.RoleNotFoundException;
import by.itacademy.railway.service.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// TODO: 08.06.2023 доделать логирование
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserRoleDto> findAll() {
        return userMapper.toListUserRoleDto(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> findById(@NotNull(message = "User id can't be null") Long id) {
        return userRepository.findById(id).map(userMapper::toUserReadDto);
    }

    @Transactional
    public boolean create(@Valid UserStringDto userStringDto) {
        userRepository.save(userMapper.toEntity(userStringDto));
        return userRepository.existsByEmail(userStringDto.getEmail());
    }

    @Transactional
    public boolean remove(@NotNull(message = "User id can't be null") Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Transactional
    public void updateRole(@NotNull(message = "User id can't be null") Long id,
                           @Valid RoleDto roleDto) throws RoleNotFoundException, UserNotFoundException {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            var optionalRole = roleRepository.findByRole(roleDto.getRole());
            if (optionalRole.isPresent()) {
                var user = optionalUser.get();
                var role = optionalRole.get();
                user.setRole(role);
                userRepository.save(user);
                roleRepository.save(role);
            } else {
                throw new RoleNotFoundException(roleDto.getRole());
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
