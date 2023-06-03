package by.itacademy.railway.mapper;

import by.itacademy.railway.dto.RoleDto;
import by.itacademy.railway.entity.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {

    Role toEntity(RoleDto roleDto);

}
