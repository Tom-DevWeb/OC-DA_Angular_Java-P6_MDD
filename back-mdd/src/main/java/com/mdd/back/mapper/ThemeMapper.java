package com.mdd.back.mapper;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.dto.responses.ThemesUserResponseDto;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    List<ThemeResponseDto> themesToThemeResponseDto(List<Theme> themes);

    @Mapping(source = "id.theme", target = "themeId")
    ThemesUserResponseDto themeToThemeUserResponseDto(ThemeSubscription themeSubscription);

    List<ThemesUserResponseDto> themesToThemesUserResponseDto(List<ThemeSubscription> themeSubscriptions);
}
