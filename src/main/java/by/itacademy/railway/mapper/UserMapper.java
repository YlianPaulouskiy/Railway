package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserStringDto userStringDto);

    UserReadDto toUserReadDto(User user);

    List<UserReadDto> toListUserRoleDto(List<User> users);

}
