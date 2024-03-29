package com.cos.shareHere.web.api;

import com.cos.shareHere.config.auth.PrincipalDetails;
import com.cos.shareHere.domain.comment.Comment;
import com.cos.shareHere.service.CommentService;
import com.cos.shareHere.web.dto.CMRespDto;
import com.cos.shareHere.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(
            @Valid @RequestBody CommentDto commentDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(),
                principalDetails.getUser().getId());
        return new ResponseEntity<>(
            new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable Integer id) {
        commentService.댓글삭제(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
    }
    
}
