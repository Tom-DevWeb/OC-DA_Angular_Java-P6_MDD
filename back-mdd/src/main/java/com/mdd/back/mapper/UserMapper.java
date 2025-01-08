package com.mdd.back.mapper;

import com.mdd.back.dto.responses.UserResponseDto;
import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "realUsername")
    User toUser(RegisterRequestDto userDto);

    @Mapping(source = "realUsername", target = "username")
    UserResponseDto toUserDto(User user);

    @Mapping(source = "username", target = "realUsername")
    void updateUserFromDto(ModifyUserRequestDto userDto, @MappingTarget User user);

}
