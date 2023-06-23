package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "fio", expression = "java(String.join(\" \", passenger.getName().toUpperCase(), " +
                                          " passenger.getLastName().toUpperCase(), " +
                                          " passenger.getMiddleName()).toUpperCase())")
    PassengerReadDto toReadDto(Passenger passenger);

    Passenger toModel(PassengerStringDto passengerStringDto);

}
