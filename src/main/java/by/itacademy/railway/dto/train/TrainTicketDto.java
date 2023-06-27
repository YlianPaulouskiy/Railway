package by.itacademy.railway.dto.train;

import lombok.*;

@Value
@Builder
public class TrainTicketDto {

    Long id;
    String code;
    String type;

}
