package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.seat.SeatReadDto;
import by.itacademy.railway.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = WagonMapper.class)
public interface SeatMapper {

    @Mappings({
            @Mapping(target = "no", expression = "java(seat.getSeatInfo().getNo())"),
            @Mapping(target = "type", expression = "java(seat.getSeatInfo().getType().getString())")
    })
    SeatReadDto toReadDto(Seat seat);

}
