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
        postRepository.save(post);
        return post.getId();
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public void delete(Long postId) {
        postRepository.delete(postId);
    }
}
