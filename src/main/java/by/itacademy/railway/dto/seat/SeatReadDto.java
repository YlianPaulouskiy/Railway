package by.itacademy.railway.dto.seat;

import by.itacademy.railway.dto.wagon.WagonReadDto;
import lombok.*;

@Value
@Builder
public class SeatReadDto {

    Long id;
    Short no;
    Double cost;
    String type;
    WagonReadDto wagon;


}
