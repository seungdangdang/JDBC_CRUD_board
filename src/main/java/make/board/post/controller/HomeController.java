package make.board.post.controller;

import java.util.List;
import make.board.post.domain.Post;
import make.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dummy_create")
    public String dummyCreate() {
        postService.testDataGenerator();
        return "redirect:/";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("data", "게시글 등록하기");
        return "create";
    }

    @GetMapping("view")
    public String view(Model model, @RequestParam(defaultValue = "1") Long page) {
        List<Post> pagedPosts = postService.findPostsByPage(page);
        Long totalPages = postService.getTotalPages();

        model.addAttribute("pagePosts", pagedPosts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "view";
    }
}
