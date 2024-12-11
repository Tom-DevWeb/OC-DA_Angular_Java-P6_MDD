package com.mdd.back.security.mapper;

import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequestDto userDto);
}
