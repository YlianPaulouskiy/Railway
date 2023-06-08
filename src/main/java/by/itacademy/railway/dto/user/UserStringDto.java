package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserStringDto extends UserDto {

    @Size(min = 8)
    @NotBlank(message = "The password must consist of at least 8 characters")
    String password;

}
