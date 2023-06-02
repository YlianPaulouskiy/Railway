package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.StationDto;
import by.itacademy.railway.entity.Station;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface StationMapper {

    StationDto toDto(Station station);

    List<StationDto> toListDto(List<Station> stations);

    Station toEntity(StationDto stationDto);

}
