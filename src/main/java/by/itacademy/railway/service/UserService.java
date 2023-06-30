package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.Order;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: 08.06.2023 доделать логирование
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderService orderService;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserReadDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserReadDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> findById(@NotNull(message = "User id can't be null") Long id) {
        return userRepository.findById(id).map(userMapper::toUserReadDto);
    }

    @Transactional
    public Optional<UserReadDto> create(@Valid UserStringDto userStringDto) {
        var user = userRepository.save(userMapper.toEntity(userStringDto));
        roleRepository.findByRole("USER").ifPresent(user::setRole);
        return Optional.ofNullable(userMapper.toUserReadDto(user));
    }

    @Transactional
    public boolean remove(@NotNull(message = "User id can't be null") Long id) {
        deleteAllOrdersWhichLinkedAtUser(id);
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

    /**
     * Удаляет заказы которые ссылаются на текущего пользователя
     *
     * @param id идентификационный номер заказа
     */
    private void deleteAllOrdersWhichLinkedAtUser(Long id) {
        userRepository.findById(id).ifPresent(user -> user.getOrders()
                .stream()
                .map(Order::getId)
                .forEach(orderService::remove));
    }
}
