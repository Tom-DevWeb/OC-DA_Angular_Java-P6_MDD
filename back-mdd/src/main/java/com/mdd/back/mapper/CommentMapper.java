package com.mdd.back.mapper;

import com.mdd.back.dto.requests.CommentRequestDto;
import com.mdd.back.dto.responses.CommentResponseDto;
import com.mdd.back.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    CommentResponseDto commentsToCommentResponseDto(Comment comment);

    List<CommentResponseDto> commentsToCommentsResponseDto(List<Comment> comments);

    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);
}
