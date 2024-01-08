package make.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PostController {

    @PostMapping("/post/new")
//    @ResponseStatus(HttpStatus.OK)
    public String handlePostRequest(@RequestParam(name = "inputName") String name,
                                    @RequestParam(name = "inputTitle") String title,
                                    @RequestParam(name = "inputContent") String content) {
        //로그용
        System.out.println(name + title + content);

//        return ResponseEntity.badRequest().build();

        return "redirect:/";
    }
}
