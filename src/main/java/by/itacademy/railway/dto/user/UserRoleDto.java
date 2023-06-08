package by.itacademy.railway.dto.user;

import by.itacademy.railway.dto.role.RoleDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRoleDto extends UserReadDto {

    private RoleDto role;

}
