package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.role.RoleStringDto;
import by.itacademy.railway.repository.RoleRepository;
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
    private RoleStringDto createdRoleDto;
    private static final Integer REMOVE_ROLE_ID = 1;


    @BeforeEach
    void setUp() {
        createdRoleDto = RoleStringDto.builder()
                .name("MANAGER")
                .build();
    }

    @Test
    @Rollback
    void createTest() {
        assertEquals(DEFAULT_SIZE, roleRepository.findAll().size());
        var optionalRoleDto = roleService.create(createdRoleDto);
        assertTrue(optionalRoleDto.isPresent());
        assertEquals(CREATED_SIZE, roleRepository.findAll().size());
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(roleRepository.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = roleService.remove(REMOVE_ROLE_ID);
        assertThat(isRemove).isTrue();
        assertThat(roleRepository.findAll()).hasSize(REMOVED_SIZE);
    }

}
