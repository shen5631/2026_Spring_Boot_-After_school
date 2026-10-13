    package com.mirim.board1.repository;

    import com.mirim.board1.Post;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface PostRepository extends JpaRepository<Post,Long> {

        //오타 주의
        //select * from posts where title = ''
        //List<Post> findByTitle(String keyword);
        //select * from posts where title like '%%'
        //findBy = select
        //Title = from posts
        //Containing = where title
        List<Post> findByTitleContaining(String keyword);
    }

