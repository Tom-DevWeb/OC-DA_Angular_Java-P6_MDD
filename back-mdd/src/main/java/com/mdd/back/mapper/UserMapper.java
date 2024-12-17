package com.mdd.back.mapper;

import com.mdd.back.dto.UserDto;
import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequestDto userDto);

    UserDto toUserDto(User user);

    void updateUserFromDto(ModifyUserRequestDto userDto, @MappingTarget User user);

}
