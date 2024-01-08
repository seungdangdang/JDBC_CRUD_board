package make.board.controller;

import make.board.domain.Post;
import make.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        return "redirect:/";
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
}
