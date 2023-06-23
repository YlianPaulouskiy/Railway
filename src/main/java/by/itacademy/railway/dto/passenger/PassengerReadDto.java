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
public class PassengerReadDto {

    private Long id;
    private String fio;
    private Gender gender;
    private DocumentType document;
    private String documentNo;

}
