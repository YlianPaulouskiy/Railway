package by.itacademy.railway.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleStringDto {

    @NotBlank(message = "Role name is required")
    String name;

}
