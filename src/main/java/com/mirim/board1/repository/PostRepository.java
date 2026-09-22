    package com.mirim.board1.repository;

    import com.mirim.board1.Post;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface PostRepository extends JpaRepository<Post,Long> {

        //오타 주의
        List<Post> findByTitleContaining(String keyword);
    }

