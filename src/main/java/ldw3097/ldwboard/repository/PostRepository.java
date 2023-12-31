package ldw3097.ldwboard.repository;


import jakarta.persistence.EntityManager;
import ldw3097.ldwboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository  {

    private final EntityManager em;
    public static final int pageLimit = 10;

    @Transactional
    public void save(Post post){
        em.persist(post);
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findPage(String boardName, int page){
        int pageLimit = 10;
        return em.createQuery("select p from Post p where p.board.id = :boardName ORDER BY p.id DESC ", Post.class)
                .setParameter("boardName", boardName)
                .setFirstResult((page-1)*pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }

    public Long countPage(String boardId){
        return em.createQuery("select count(*) from Post p where p.board.id = :boardId", Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();

    }
    @Transactional
    public void delete(Post post){
        em.remove(post);
    }


}
