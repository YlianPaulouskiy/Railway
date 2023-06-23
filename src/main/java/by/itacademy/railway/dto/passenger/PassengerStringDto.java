package by.itacademy.railway.dto.passenger;

import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value
@Builder
public class PassengerStringDto {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @NotBlank(message = "Middle name is required")
    String middleName;
    @NotNull(message = "Gender can't  be null")
    Gender gender;
    @NotNull(message = "Document can't be null")
    DocumentType document;
    @NotBlank(message = "Document number is required")
    String documentNo;

}
