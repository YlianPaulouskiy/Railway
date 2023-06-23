package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.dto.station.StationStringDto;
import by.itacademy.railway.entity.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    StationReadDto toDto(Station station);

    List<StationReadDto> toListDto(List<Station> stations);

    Station toEntity(StationStringDto stationStringDto);

}
