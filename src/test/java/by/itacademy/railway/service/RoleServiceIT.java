package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.role.RoleDto;
import by.itacademy.railway.repository.RoleRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class RoleServiceIT {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private static final Integer DEFAULT_SIZE = 2;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private static final String EXCEPTION_MESSAGE = "Role name is required";
    private RoleDto createdRoleDto;
    private static final String REMOVE_ROLE = "ADMIN";


    @BeforeEach
    void setUp() {
        createdRoleDto = RoleDto.builder()
                .role("MANAGER")
                .build();
    }

    @Test
    @Rollback
    void createTest() {
        assertEquals(DEFAULT_SIZE, roleRepository.findAll().size());
        boolean isCreated = roleService.create(createdRoleDto);
        assertTrue(isCreated);
        assertEquals(CREATED_SIZE, roleRepository.findAll().size());
    }

    @Test
    void validationTest() {
        createdRoleDto.setRole("");
        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> roleService.create(createdRoleDto));
        assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE));
        assertThrows(ConstraintViolationException.class, () -> roleService.remove(""));
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(roleRepository.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = roleService.remove(REMOVE_ROLE);
        assertThat(isRemove).isTrue();
        assertThat(roleRepository.findAll()).hasSize(REMOVED_SIZE);
    }

}
