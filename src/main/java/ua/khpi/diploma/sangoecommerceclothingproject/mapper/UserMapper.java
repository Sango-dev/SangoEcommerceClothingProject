package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.UserDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto dto);

    @InheritInverseConfiguration
    UserDto fromUser(User user);

    List<User> toUserList(List<UserDto> dtos);

    List<UserDto> fromUserList(List<User> list);

}