package by.itacademy.railway.dto.station;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StationStringDto {

    @NotBlank(message = "Station name is required")
    String name;

}
