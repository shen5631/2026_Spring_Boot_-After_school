package com.mirim.board1.dto;

import com.mirim.board1.Comment;

import java.time.LocalDateTime;

public class CommentResopnse {
    public CommentResopnse(Long id, String content, AuthorResponse author, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    private final Long id;
    private final String content;
    private final AuthorResponse author;
    private final LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public AuthorResponse getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static CommentResopnse from(Comment comment){
        return new CommentResopnse(
                comment.getId(),
                comment.getContent(),
                AuthorResponse.from(comment.getAuthor()),
                comment.getCreatedAt()
        );
    }
}
