package com.mirim.board1;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    private SmsNotfier notifier = new SmsNotfier();

    //특정게시물 불러오기
    //읽는 방법
    //많은 데이터 게시글 어떻게 보낼까?

    @GetMapping()
    public String searchPosts(@RequestParam(required = false) String keyword){
        if(keyword != null){
            return keyword + "(으)로 검색한 결과입니다";
        }
        else{
            return "쿼리 파라미터로 검색한결과입니다.";
        }
    }
    @GetMapping("/count")
    public String getPostsCount(){
        return "게시글 개수: 0개";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        //게시글 번호가 10번보다 크면 게시글이 없는거임
        if(id > 10){
            //404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다");
        }
        else if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("번호를 다시 한번 확인해주세요");
        }
        //200
        return ResponseEntity.status(HttpStatus.OK).body(id+"번 게시글입니다.");
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String,Object> request){
        String title = (String)request.get("title");
        String content = (String)request.get("content");
        Long user_id = (Long)request.get("user_id");

        //db에 데이터를 정장한다고 치고

        Map<String, Object> response = new HashMap<>();
        response.put("title",title);
        response.put("content",content);
        response.put("message","게시글이 등록되었습니다.");

        //이메일 발송
        notifier.send(title+" 게시글이 등록되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
         //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Map<String,Object> request){

        if(id > 10){
            //404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다");
        }
        else if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("번호를 다시 한번 확인해주세요");
        }


        String title = (String)request.get("title");
        String content = (String)request.get("content");

        Map<String, Object> response = new HashMap<>();
        response.put("title",title);
        response.put("content",content);
        response.put("message","게시글이 등록되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다시 입력해주세요");
        }
        if(id > 10){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않습니다.");
        }


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
