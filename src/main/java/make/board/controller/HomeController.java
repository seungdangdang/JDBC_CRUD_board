package make.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("update")
    public String update(Model model) {
        model.addAttribute("data", "게시글 등록하기");
        return "update";
    }
}
