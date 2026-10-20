package com.mirim.board1.dto;

import com.mirim.board1.Member;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class MemberResponse {
    private final Long id;
    private final String loginId;
    private final String name;
    private final LocalDateTime createdAt;

    private MemberResponse(Long id, String loginId, String name, LocalDateTime createdAt) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static MemberResponse from(Member member){
        return new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
