package com.mirim.board1.dto;

import com.mirim.board1.Member;

public class AuthorResponse {
    private final Long id;
    private final String name;

    public AuthorResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static AuthorResponse from(Member member){
        return new AuthorResponse(member.getId(), member.getName());
    }
}
