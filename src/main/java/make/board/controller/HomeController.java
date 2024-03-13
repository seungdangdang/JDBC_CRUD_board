package make.board.controller;

import java.util.List;
import make.board.domain.Post;
import make.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("data", "게시글 등록하기");
        return "create";
    }

    @GetMapping("view")
    public String view(Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "view";
    }
}
