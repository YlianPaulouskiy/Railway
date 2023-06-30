package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import by.itacademy.railway.entity.RouteStation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = StationMapper.class)
public interface RouteStationMapper {

    RouteStationReadDto toReadDto(RouteStation routeStation);

}
