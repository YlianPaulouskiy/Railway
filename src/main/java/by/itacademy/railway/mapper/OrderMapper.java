package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.order.OrderReadDto;
import by.itacademy.railway.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = TicketMapper.class)
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "no", expression = "java(order.getOrderInfo().getNo())"),
            @Mapping(target = "status", expression = "java(order.getOrderInfo().getStatus().getString())"),
            @Mapping(target = "registrationTime", expression = "java(order.getOrderInfo().getRegistrationTime())"),
            @Mapping(target = "payedTime", expression = "java(order.getOrderInfo().getPayedTime())")
    })
    OrderReadDto toReadDto(Order order);

}
