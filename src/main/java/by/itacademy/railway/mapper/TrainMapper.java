package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.train.TrainReadDto;
import by.itacademy.railway.dto.train.TrainSearchDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.entity.Train;
import by.itacademy.railway.entity.Wagon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class TrainMapper {

    @Mappings({
            @Mapping(target = "code", expression = "java(train.getTrainInfo().getCode())"),
            @Mapping(target = "type", expression = "java(train.getTrainInfo()" +
                                                   ".getType().getString())")
    })
    public abstract TrainReadDto toReadDto(Train train);

    public TrainSearchDto toSearchDto(Train train, String from, String to) {
        return TrainSearchDto.builder()
                .id(train.getId())
                .code(train.getTrainInfo().getCode())
                .type(train.getTrainInfo().getType().toString())
                .departureStation(from)
                .arrivalStation(to)
                .departureTime(getDepartureTime(train, from))
                .arrivalTime(getArrivalTime(train, to))
                .seatsCount(getSeatsCount(train))
                .build();
    }

    private Integer getSeatsCount(Train train) {
        var count = 0;
        for (Wagon wagon : train.getWagons()) {
            count += wagon.getSeats().size();
        }
        return count;
    }

    private LocalDateTime getArrivalTime(Train train, String to) {
        for (RouteStation station : train.getRouteStations()) {
            if (station.getStation().getName().equals(to)) {
                return station.getArrivalTime();
            }
        }
        throw new RuntimeException("Arrival time not found.");
    }

    private LocalDateTime getDepartureTime(Train train, String from) {
        for (RouteStation station : train.getRouteStations()) {
            if (station.getStation().getName().equals(from)) {
                return station.getDepartureTime();
            }
        }
        throw new RuntimeException("Departure time not found.");
    }

}
