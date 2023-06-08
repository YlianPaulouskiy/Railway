package by.itacademy.railway.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserReadDto extends UserDto {

    private Long id;

}
