package by.itacademy.railway.dto.train;

import lombok.*;

@Value
@Builder
public class TrainReadDto {

    Long id;
    String code;
    String type;

}
