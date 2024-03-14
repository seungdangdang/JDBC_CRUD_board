package make.board.service;

import java.util.List;
import java.util.Optional;
import make.board.domain.Post;
import make.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long join(Post post) {
        validateJoin(post);
        postRepository.save(post);
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
        if (post.getAuthor().isEmpty() || post.getTitle().isEmpty() || post.getContent().isEmpty()) {
            throw new NullPointerException("모두 입력하세요.");
        }
    }
}
