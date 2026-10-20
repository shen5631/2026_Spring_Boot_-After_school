package com.mirim.board1;

import com.mirim.board1.dto.ApiErrorResponse;
import com.mirim.board1.dto.LoginRequest;
import com.mirim.board1.dto.MemberResponse;
import com.mirim.board1.dto.SignupRequest;
import com.mirim.board1.exception.ApiException;
import com.mirim.board1.login.SessionConst;
import com.mirim.board1.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody SignupRequest request){
        MemberResponse created = memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?> getMember(@PathVariable Long id){
        MemberResponse member = memberService.getMember(id);
        return ResponseEntity.ok(member);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e){
        ApiErrorResponse body = new ApiErrorResponse(e.getStatus().value(),e.getMessage(), Map.of());
        return ResponseEntity.status(e.getStatus()).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest){
        MemberResponse member = memberService.login(request);

        //사용자별 보관함
        HttpSession session = httpRequest.getSession();
        //로그인이 성공했으니 번호표 교체
        httpRequest.changeSessionId();
        //세션에 사용자 pk 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER_ID,member.getId());
        return ResponseEntity.ok(member);
    }
}
