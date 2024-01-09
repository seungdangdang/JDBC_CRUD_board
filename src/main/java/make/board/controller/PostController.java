package make.board.controller;

import java.util.Optional;
import make.board.domain.Post;
import make.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/new")
    public String create(@ModelAttribute Post post) {
        // 로그용
        System.out.println(post.getInputName() + post.getInputTitle() + post.getInputContent());

        postService.join(post);

        return "redirect:/view";
    }
//    @ResponseStatus(HttpStatus.OK)
//    public String handlePostRequest(@RequestParam(name = "inputName") String name,
//                                    @RequestParam(name = "inputTitle") String title,
//                                    @RequestParam(name = "inputContent") String content) {
//        로그용
//        System.out.println(name + title + content);

////        return ResponseEntity.badRequest().build();
//        return "redirect:/";
//    }

    @GetMapping("/detail")
    public String showDetailPage(@RequestParam(name = "postId") Long postId, Model model) {
        Optional<Post> optionalPost = postService.findPostById(postId);
        optionalPost.ifPresent(post -> model.addAttribute("post", post));

        return "detail";
    }
}
