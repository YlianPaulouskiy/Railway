package by.itacademy.railway.dto.ticket;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.seat.SeatTicketDto;
import lombok.*;

@Value
@Builder
public class TicketReadDto {

    Long id;
    Double cost;
    PassengerReadDto passenger;
    SeatTicketDto seat;

}
