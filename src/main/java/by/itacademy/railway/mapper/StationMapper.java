package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.station.StationDto;
import by.itacademy.railway.entity.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    StationDto toDto(Station station);

    List<StationDto> toListDto(List<Station> stations);

    Station toEntity(StationDto stationDto);

}
