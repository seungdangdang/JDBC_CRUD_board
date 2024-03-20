package make.board.post.controller;

import java.util.Optional;
import make.board.post.domain.Post;
import make.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/new")
    public String create(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        try {
            // 로그용
            System.out.println(post.getAuthor() + post.getTitle() + post.getContent());

            postService.join(post);
            return "redirect:/view";
        } catch (NullPointerException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "모두 입력하세요.");
            return "redirect:/create";
        }
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

    //    유효한 delete매핑 (1)
    @GetMapping("/delete")
    public String deletePost(@RequestParam(name = "postId") Long postId) {
        postService.delete(postId);
        return "redirect:/view";
    }

//    유효한 delete매핑 (2)
//    @GetMapping("/delete/{postId}")
//    public String deletePost(@PathVariable(name = "postId") Long postId) {
//        postService.delete(postId);
//        return "redirect:/view";
//    }

//    잘못된매핑예시
//    @GetMapping("/delete/{postId}")
//    public String deletePost(@PathVariable Long postId) {
//        postService.delete(postId);
//        return "redirect:/view";
//    }
}
