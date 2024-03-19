package make.board.user.controller;

import make.board.user.domain.SiteUser;
import make.board.user.service.UserCreateFormValidator;
import make.board.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserCreateFormValidator validator;

    public UserController(UserService userService, UserCreateFormValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("data", "회원가입 하기");
        model.addAttribute("siteUser", new SiteUser());
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SiteUser siteUser, Model model) {
        try {
            // 로그용
            System.out.println(siteUser.toString());

            validator.validate(siteUser);
            userService.create(siteUser);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("siteUser", siteUser);
            return "signup_form";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}