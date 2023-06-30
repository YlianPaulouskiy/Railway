package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.ticket.TicketReadDto;
import by.itacademy.railway.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SeatMapper.class, PassengerMapper.class, RouteStationMapper.class})
public interface TicketMapper {

    TicketReadDto toReadDto(Ticket ticket);

}
