package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.role.RoleStringDto;
import by.itacademy.railway.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "name", target = "role")
    Role toEntity(RoleStringDto roleStringDto);

    @Mapping(source = "role", target = "name")
    RoleReadDto toDto(Role role);

}
