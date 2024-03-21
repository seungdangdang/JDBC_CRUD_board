package make.board.post.repository;

import java.util.List;
import java.util.Optional;
import make.board.post.domain.Post;
import make.board.user.domain.SiteUser;

public interface PostRepository {
    Post save(Post post, SiteUser siteUser);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    void delete(Long id);

    Optional<Post> modify(Long id, String newContent);

    List<Post> findPostsByPage(Long pageNumber);

    Long getToTalPages();

    Long countPosts();

    void testDataGenerator();
}
