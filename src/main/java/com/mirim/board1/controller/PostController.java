package com.mirim.board1.controller;

import com.mirim.board1.dto.PostRequest;
import com.mirim.board1.dto.PostResponse;
import com.mirim.board1.login.SessionConst;
import com.mirim.board1.repository.PostRepository;
import com.mirim.board1.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            List<PostResponse> posts =  postService.searchPosts(keyword);
            return ResponseEntity.ok(posts);
        }

        List<PostResponse> posts = postService.getAllPosts();
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
        PostResponse post = postService.getPost(id);

        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest request, @SessionAttribute(name =SessionConst.LOGIN_MEMBER_ID) Long loginMemberId){
        String title = request.getTitle();
        String content = request.getContent();
        //Long user_id = request.getClass();

        PostResponse response = postService.createPost(title,content, loginMemberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
         //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid@RequestBody PostRequest request){

        String title = request.getTitle();
        String content = request.getContent();
        PostResponse response = postService.updatePost(title,content,id);


        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+content;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long loginMemberId) {
        postService.deletePost(id, loginMemberId);
        return ResponseEntity.noContent().build();
    }

}
