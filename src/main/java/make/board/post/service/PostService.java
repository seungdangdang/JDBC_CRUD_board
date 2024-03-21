package make.board.post.service;

import java.util.List;
import java.util.Optional;
import make.board.post.domain.Post;
import make.board.post.repository.PostRepository;
import make.board.user.domain.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long join(Post post, SiteUser siteUser) {
        validateJoin(post);
        postRepository.save(post, siteUser);
        return post.getPostId();
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public void testDataGenerator() {
        postRepository.testDataGenerator();
    }

    public List<Post> findPostsByPage(Long pageNumber) {
        return postRepository.findPostsByPage(pageNumber);
    }

    public Long getTotalPages() {
        return postRepository.getToTalPages();
    }

    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public void delete(Long postId) {
        postRepository.delete(postId);
    }

    private void validateJoin(Post post) {
        if (post.getTitle().isEmpty() || post.getContent().isEmpty()) {
            throw new NullPointerException("모두 입력하세요.");
        }
    }
}
