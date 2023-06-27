package by.itacademy.railway.dto.seat;

import by.itacademy.railway.dto.wagon.WagonTicketDto;
import lombok.*;

@Value
@Builder
public class SeatTicketDto {

    Long id;
    Short no;
    String type;
    WagonTicketDto wagon;


}
