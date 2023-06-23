package by.itacademy.railway.dto.user;

import by.itacademy.railway.dto.role.RoleReadDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class UserReadDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private RoleReadDto role;

}
