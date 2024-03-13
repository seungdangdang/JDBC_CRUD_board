package make.board.repository;

import java.util.List;
import java.util.Optional;
import make.board.domain.Post;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    void delete(Long id);

    Optional<Post> modify(Long id, String newContent);

    List<Post> findPostsByPage(Long pageNumber);

    Long getToTalPages();

    Long countPosts();
}
