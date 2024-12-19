package com.mdd.back.dto.responses;

import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private String authorUsername;
}

