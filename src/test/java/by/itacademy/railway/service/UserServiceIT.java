package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.role.RoleDto;
import by.itacademy.railway.dto.user.UserRoleDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.validation.annotation.Validated;

import java.awt.print.Pageable;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserRepository userRepository;
    private final UserService userService;
    private static final Long FIND_ID = 1L;
    private static final Integer DEFAULT_SIZE = 2;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private User rightUser;
    private UserStringDto createUserDto;
    private RoleDto updatedRole;

    @BeforeEach
    void setUp() {
        userRepository.findById(FIND_ID).ifPresent(user -> rightUser = user);
        createUserDto = UserStringDto.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .password("1qaz2wsx3edc")
                .build();
        updatedRole = RoleDto.builder().role("USER").build();
    }

    @Test
    void findByIdTest() {
        var optionalUserDto = userService.findById(FIND_ID);
        assertTrue(optionalUserDto.isPresent());
        assertEquals(optionalUserDto.get().getId(), rightUser.getId());
        assertEquals(optionalUserDto.get().getName(), rightUser.getName());
        assertEquals(optionalUserDto.get().getLastName(), rightUser.getLastName());
        assertEquals(optionalUserDto.get().getEmail(), rightUser.getEmail());
    }

    @Test
    void findAllTest() {
        var users =userService.findAll();
        assertThat(users).hasSize(DEFAULT_SIZE);
        var emails = users.stream().map(UserRoleDto::getEmail).collect(Collectors.toList());
        assertThat(emails).containsExactlyInAnyOrder("maxik@gmail.com", "kate99@gamil.com");
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(userService.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userService.create(createUserDto));
        assertThat(userService.findAll()).hasSize(CREATED_SIZE);
        assertThat(userService.login(createUserDto.getEmail(), createUserDto.getPassword())).isPresent();
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(userService.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userService.remove(FIND_ID));
        assertThat(userService.findAll()).hasSize(REMOVED_SIZE);
        assertThat(userService.findById(FIND_ID)).isEmpty();
    }

    @Test
    @Rollback
    void updateTest() {
        assertNotEquals(rightUser.getRole().getRole(), updatedRole.getRole());
        userService.updateRole(FIND_ID, updatedRole);
        assertEquals(rightUser.getRole().getRole(), updatedRole.getRole());
    }

    @Test
    void loginTest() {
        var optionalUserDto = userService.login(rightUser.getEmail(), rightUser.getPassword());
        assertTrue(optionalUserDto.isPresent());
        assertEquals(optionalUserDto.get().getId(), rightUser.getId());
        assertEquals(optionalUserDto.get().getName(), rightUser.getName());
        assertEquals(optionalUserDto.get().getLastName(), rightUser.getLastName());
        assertEquals(optionalUserDto.get().getEmail(), rightUser.getEmail());
    }

    @Test
    void validationTest() {
        createUserDto.setName(null);
        assertThrows(ConstraintViolationException.class, () -> userService.create(createUserDto));
        createUserDto.setName("Name");
        createUserDto.setLastName("");
        assertThrows(ConstraintViolationException.class, () -> userService.create(createUserDto));
        createUserDto.setLastName("LAStName");
        createUserDto.setEmail("asdafs");
        assertThrows(ConstraintViolationException.class, () -> userService.create(createUserDto));
        createUserDto.setEmail("example@gmail.com");
        createUserDto.setPassword("4444");
        assertThrows(ConstraintViolationException.class, () -> userService.create(createUserDto));
        assertThrows(ConstraintViolationException.class, () -> userService.findById(null));
        assertThrows(ConstraintViolationException.class, () -> userService.remove(null));
        assertThrows(ConstraintViolationException.class, () -> userService.updateRole(null, updatedRole));
        assertThrows(ConstraintViolationException.class, () -> userService.login("null",""));
    }

    @Test
    void pageTest() {
        var pageable = PageRequest.of(0,2);
        var users = userService.findAll(pageable);
        assertThat(users).hasSize(2);
    }
}
