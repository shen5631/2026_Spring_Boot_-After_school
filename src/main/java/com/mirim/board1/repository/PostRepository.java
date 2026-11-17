    package com.mirim.board1.repository;

    import com.mirim.board1.Post;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

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

        @Query(value = "select p from Post p join fetch p.author", countQuery = "select count(p) from Post p")
        Page<Post> findAllWithAuthor(Pageable pageable);
    }

