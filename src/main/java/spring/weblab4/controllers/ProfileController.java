package spring.weblab4.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("my-profile")
    public String myProfilePage(@ModelAttribute("user") User user, Authentication authentication,
                                Model model){
        model.addAttribute(userRepository.findByUsername(authentication.getName()).get());
        return "my-profile";
    }

    @PostMapping("update-profile")
    public String updateProfile(@ModelAttribute("user") User user, Authentication authentication){
        User tmpUser = userRepository.findByUsername(authentication.getName()).get();
        tmpUser.setFirst_name(user.getFirst_name().equals("") ? null : user.getFirst_name());
        tmpUser.setMiddle_name(user.getMiddle_name().equals("") ? null : user.getMiddle_name());
        tmpUser.setLast_name(user.getLast_name().equals("") ? null : user.getLast_name());
        userRepository.save(tmpUser);
        return "redirect:my-profile";
    }
}