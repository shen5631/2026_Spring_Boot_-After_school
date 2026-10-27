package com.mirim.board1.controller;

import com.mirim.board1.dto.CommentRequest;
import com.mirim.board1.dto.CommentResopnse;
import com.mirim.board1.login.SessionConst;
import com.mirim.board1.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResopnse> createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequest request, @SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long loginMemberId){
        CommentResopnse created = commentService.createComment(postId,request,loginMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResopnse>> getComments(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId ,@PathVariable Long commentId , @SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long loginMemberId){
        commentService.deleteComment(commentId,loginMemberId);
        return  ResponseEntity.noContent().build();
    }
}
