package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class UserDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Lastname is required")
    private String lastName;
    @Email(message = "Email is incorrect, follow the example: mymail@gmail.com")
    private String email;

}
