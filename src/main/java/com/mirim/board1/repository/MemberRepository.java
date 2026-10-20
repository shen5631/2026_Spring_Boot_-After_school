package com.mirim.board1.repository;

import com.mirim.board1.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    //loginId와 같은 회원이 있는지 boolean으로 리턴
    boolean existsByLoginId(String loginId);


    //loginId로 Member조화
    Optional<Member> findByLoginId(String loginId);
}
