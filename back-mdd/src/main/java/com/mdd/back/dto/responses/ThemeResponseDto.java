package com.mdd.back.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThemeResponseDto {

    private Long id;
    private String title;
    private String description;
}
