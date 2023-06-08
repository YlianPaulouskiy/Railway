package by.itacademy.railway.dto.passenger;

import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerStringDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Lastname is required")
    private String lastName;
    @NotBlank(message = "Middle name is required")
    private String middleName;
    @NotNull(message = "Gender can't  be null")
    private Gender gender;
    @NotNull(message = "Document can't be null")
    private DocumentType document;
    @NotBlank(message = "Document number is required")
    private String documentNo;

}
