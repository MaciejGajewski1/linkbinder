package projekt.wsb.linkbinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import projekt.wsb.linkbinder.controllers.users.UserDto;

import javax.validation.Valid;


@Controller
class RegistrationController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register_approve").setViewName("register_approve");
    }

    @GetMapping("/redirectToSignUpPage")
    String showForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register_form";
    }

    @GetMapping("/register")
    String showForm(UserDto user) {
        return "register_form";
    }

    @PostMapping("/register")
    String AddValidatedPerson(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register_form";
        }
        return "redirect:/register_approve";
    }

    @GetMapping("/redirectToHomePage")
    String showHomePage() {
        return "index";
    }

}
