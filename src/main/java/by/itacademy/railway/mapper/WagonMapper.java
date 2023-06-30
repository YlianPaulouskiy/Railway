package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.wagon.WagonReadDto;
import by.itacademy.railway.entity.Wagon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = TrainMapper.class)
public interface WagonMapper {

    @Mappings({
            @Mapping(target = "no", expression = "java(wagon.getWagonInfo().getNo())"),
            @Mapping(target = "type", expression = "java(wagon.getWagonInfo().getType().getString())")
    })
    WagonReadDto toReadDto(Wagon wagon);

}
