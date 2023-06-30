package by.itacademy.railway.dto.ticket;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.seat.SeatReadDto;
import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import lombok.*;

@Value
@Builder
public class TicketReadDto {

    Long id;
    RouteStationReadDto from;
    RouteStationReadDto to;
    PassengerReadDto passenger;
    SeatReadDto seat;

}
