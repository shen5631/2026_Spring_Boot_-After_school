package com.mirim.board1.service;

import com.mirim.board1.Member;
import com.mirim.board1.Notifier;
import com.mirim.board1.Post;
import com.mirim.board1.dto.PostResponse;
import com.mirim.board1.exception.ApiException;
import com.mirim.board1.repository.CommentRepository;
import com.mirim.board1.repository.MemberRepository;
import com.mirim.board1.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private  final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final Notifier notifier;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, Notifier notifier, MemberService memberService,MemberRepository memberRepository,CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.notifier = notifier;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id).orElse(null);
        if(post == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }
        return PostResponse.from(post);
    }
    public PostResponse createPost(String title, String content, Long loginMemberId){
        Member author = memberRepository.findById(loginMemberId).orElse(null);
        if(author == null){
            throw new ApiException(HttpStatus.NOT_FOUND,"존재하지 않는 회원입니다.");
        }
        Post post = new Post(title,content,author);
        postRepository.save(post);

        //이메일 발송
        notifier.send(title+" 게시글이 등록되었습니다.");

        return PostResponse.from(post);
    }
    public PostResponse updatePost(String title, String content, Long id){
        Post post =  postRepository.findById(id).orElse(null);


        if(post == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다");
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);

        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long id, Long loginMemberId) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }
        if (!post.getAuthor().getId().equals(loginMemberId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
        commentRepository.deleteAll(commentRepository.findByPostId(id));
        postRepository.delete(post);
    }

    public Page<PostResponse> getAllPosts(Pageable pageable){
            return (postRepository.findAllWithAuthor(pageable).map(PostResponse::from));
    }

    public long getPostCount(){
        return postRepository.count();
    }

    public List<PostResponse> searchPosts(String keyword){
        return toResponses(postRepository.findByTitleContaining(keyword));
    }

    private List<PostResponse> toResponses(List<Post> posts){
        List<PostResponse> responses = new ArrayList<>();
        for(Post post : posts){
            responses.add(PostResponse.from(post));
        }
        return responses;
    }

}
