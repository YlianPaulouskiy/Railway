package by.itacademy.railway.dto.wagon;

import by.itacademy.railway.dto.train.TrainTicketDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WagonTicketDto {

    Long id;
    Short no;
    String type;
    TrainTicketDto train;

}
