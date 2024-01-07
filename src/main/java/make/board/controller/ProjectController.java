package make.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("update")
    public String hello(Model model) {
        model.addAttribute("data", "게시글 등록하기");
        return "update";
    }
}