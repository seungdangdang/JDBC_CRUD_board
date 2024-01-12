package make.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import make.board.domain.Post;
import make.board.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    void join() {
        Post post = new Post();
        post.setInputName("홍길동");
        post.setInputTitle("홍길동전");
        post.setInputContent("옛날옛적에");

        Long id = postService.join(post);

        Optional<Post> findPost = postService.findPostById(id);

        // Optional에서 값 가져오기
        Post actualPost = findPost.orElseThrow(() -> new AssertionError("게시물을 찾을 수 없습니다."));

        // 테스트 검증
        assertThat(actualPost.getInputName()).isEqualTo("홍길동");
        assertThat(actualPost.getInputTitle()).isEqualTo("홍길동전");
        assertThat(actualPost.getInputContent()).isNotEqualTo("옛날옛적에말입니다");
    }

    @Test
    void findPostById() {
    }

    @Test
    void delete() {
        Post post = new Post();
        post.setInputName("홍길동");
        post.setInputTitle("홍길동전");
        post.setInputContent("옛날옛적에");

        Long id = postService.join(post);

        Optional<Post> findPost = postService.findPostById(id);

        // Optional에서 값 가져오기
        Post actualPost = findPost.orElseThrow(() -> new AssertionError("게시물을 찾을 수 없습니다."));

        // 테스트 검증
        assertThat(actualPost.getInputName()).isEqualTo("홍길동");
        assertThat(actualPost.getInputTitle()).isEqualTo("홍길동전");
        assertThat(actualPost.getInputContent()).isNotEqualTo("옛날옛적에말입니다");

        postService.delete(id);

        Optional<Post> secondPost = postService.findPostById(id);

        // Optional에서 값 가져오기
        assertThat(secondPost).isEmpty();
    }
}