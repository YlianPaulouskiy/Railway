package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Value
@Builder
public class UserStringDto {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @Email(message = "Email is incorrect, follow the example: mymail@gmail.com")
    String email;
    @Size(min = 8)
    @NotBlank(message = "The password must consist of at least 8 characters")
    String password;

}
