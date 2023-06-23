package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

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
    private RoleReadDto updatedRole;

    @BeforeEach
    void setUp() {
        userRepository.findById(FIND_ID).ifPresent(user -> rightUser = user);
        createUserDto = UserStringDto.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .password("1qaz2wsx3edc")
                .build();
        updatedRole = RoleReadDto.builder().id(2).name("USER").build();
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
        var emails = users.stream().map(UserReadDto::getEmail).collect(Collectors.toList());
        assertThat(emails).containsExactlyInAnyOrder("maxik@gmail.com", "kate99@gamil.com");
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(userService.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userService.create(createUserDto).isPresent());
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
        assertNotEquals(rightUser.getRole().getRole(), updatedRole.getName());
        userService.updateRole(FIND_ID, updatedRole);
        assertEquals(rightUser.getRole().getRole(), updatedRole.getName());
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

}
