package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.role.RoleDto;
import by.itacademy.railway.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDto roleDto);

}
