package by.itacademy.railway.dto.train;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSearchDto {

    private Long id;
    private String code;
    private String type;
    private String departureStation;
    private LocalDateTime departureTime;
    private String arrivalStation;
    private LocalDateTime arrivalTime;
    private Integer seatsCount;

}
