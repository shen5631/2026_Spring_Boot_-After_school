package com.mirim.board1.service;

import com.mirim.board1.Notifier;
import com.mirim.board1.Post;
import com.mirim.board1.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {


    private final PostRepository postRepository;
    private final Notifier notifier;



    public PostService(PostRepository postRepository, Notifier notifier){
        this.postRepository = postRepository;
        this.notifier = notifier;
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElse(null);
    }
    public Post createPost(String title, String content){
        Post post = new Post(title,content);
        postRepository.save(post);

        //이메일 발송
        notifier.send(title+" 게시글이 등록되었습니다.");

        return post;
    }
    public Post updatePost(String title, String content, Long id){
        Post post =  postRepository.findById(id).orElse(null);


        if(post == null){
            return null;
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);

        return post;
    }

    public boolean deletePost(Long id){
        Post post = postRepository.findById(id).orElse(null);

        if(post == null){
            return false;
        }

        postRepository.deleteById(id);
        return true;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public long getPostCount(){
        return postRepository.count();
    }

    public List<Post> searchPosts(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }
}
