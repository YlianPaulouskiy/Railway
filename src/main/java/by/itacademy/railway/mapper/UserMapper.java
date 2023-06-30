package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    User toEntity(UserStringDto userStringDto);

    UserReadDto toUserReadDto(User user);

}
