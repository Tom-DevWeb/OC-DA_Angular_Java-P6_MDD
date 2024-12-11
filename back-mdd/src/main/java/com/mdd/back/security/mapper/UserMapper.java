package com.mdd.back.security.mapper;

import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);


    User UserRequestDtoToUser(RegisterRequestDto userDto);
}
