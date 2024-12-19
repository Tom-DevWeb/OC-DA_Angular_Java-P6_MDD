package com.mdd.back.mapper;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.entities.Theme;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    List<ThemeResponseDto> themesToThemeResponseDto(List<Theme> themes);
}
