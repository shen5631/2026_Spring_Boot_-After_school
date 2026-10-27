package com.mirim.board1.service;

import com.mirim.board1.Comment;
import com.mirim.board1.Member;
import com.mirim.board1.Post;
import com.mirim.board1.dto.CommentRequest;
import com.mirim.board1.dto.CommentResopnse;
import com.mirim.board1.exception.ApiException;
import com.mirim.board1.repository.CommentRepository;
import com.mirim.board1.repository.MemberRepository;
import com.mirim.board1.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public CommentResopnse createComment(Long postId, CommentRequest request, Long loginMemberId){
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다.");
        }
        Member author = memberRepository.findById(loginMemberId).orElse(null);
        if (author == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
        Comment comment = new Comment(request.getContent(), post,author);

        commentRepository.save(comment);
        return CommentResopnse.from(comment);
    }

    public List<CommentResopnse> getComments(Long postId){
        return commentRepository.findByPostIdWithAuthor(postId).stream().map(CommentResopnse::from).toList();
    }

    public void deleteComment(Long commentId, Long loginMemberId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다.");
        }
        if(!comment.getAuthor().getId().equals(loginMemberId)){
            throw new ApiException(HttpStatus.FORBIDDEN,"본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
