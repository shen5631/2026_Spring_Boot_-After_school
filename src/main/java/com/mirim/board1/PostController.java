package com.mirim.board1;

import com.mirim.board1.repository.PostRepository;
import com.mirim.board1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository){
        this.postService = postService;
        this.postRepository = postRepository;
    }


    //특정게시물 불러오기
    //읽는 방법
    //많은 데이터 게시글 어떻게 보낼까?

    @GetMapping()
    public ResponseEntity<?> getPosts(@RequestParam(required = false) String keyword){
        if(keyword != null){
            List<Map<String,Object>> posts =  postService.searchPosts(keyword);
            return ResponseEntity.ok(posts);
        }

        List<Map<String,Object>> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);

    }
    @GetMapping("/count")
    public String getPostsCount(){
        long postCount= postService.getPostCount();
        return "게시글 개수 : "+postCount+"개";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        //게시글 번호가 10번보다 크면 게시글이 없는거임
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("번호를 다시 한번 확인해주세요");
        }
        //200
        Map<String, Object> post = postService.getPost(id);
        if(post == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존자하지않는 게시물");
        }

        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String,Object> request){
        String title = (String)request.get("title");
        String content = (String)request.get("content");
        Long user_id = (Long)request.get("user_id");

        Map<String,Object> response = postService.createPost(title,content);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
         //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Map<String,Object> request){

        String title = (String)request.get("title");
        String content = (String)request.get("content");
        Map<String,Object> response = postService.updatePost(title,content,id);

        if(response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("번호를 다시 한번 확인해주세요");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다시 입력해주세요");
        }

        boolean deleted = postService.deletePost(id);
        if(deleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않습니다.");
        }


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
