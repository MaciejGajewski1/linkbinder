package projekt.wsb.linkbinder.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import projekt.wsb.linkbinder.service.UserService;
import projekt.wsb.linkbinder.users.UserDto;

import javax.validation.Valid;


@Controller
@AllArgsConstructor
class RegistrationController implements WebMvcConfigurer {

    private final UserService userService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register_approve").setViewName("register_approve");
        registry.addViewController("/register_dissapprove").setViewName("register_dissapprove");
        registry.addViewController("/password_dissapprove").setViewName("password_dissapprove");
    }

    @GetMapping("/")
    String startUp(Model model) {
        return userService.initModel(model);
    }

    @PostMapping("/login")
    String logUser(
            @ModelAttribute("user") UserDto user,
            Model model) {
        return userService.logUser(user, model);
    }

    @GetMapping("/redirectToSignUpPage")
    String showForm(Model model) {
        return userService.beginRegistration(model);
    }

    @GetMapping("/register")
    String showForm(UserDto user) {
        return "register_form";
    }

    @PostMapping("/register")
    String AddValidatedPerson(
            @Valid @ModelAttribute("user") UserDto user,
            BindingResult bindingResult,
            Model model) {
        return userService.validateAndAddPerson(user, bindingResult, model);
    }

    @GetMapping("/redirectToHomePage")
    String showHomePage(Model model) {
        return userService.returnToHomePage(model);
    }

    @RequestMapping(value = "/deleteUser")
    String deleteUser(
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return userService.deleteUser(loggedUsername, model);
    }

    @RequestMapping(value = "/deleteUserApprove")
    String deleteUserApprove(
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return userService.deleteUserApprove(loggedUsername, model);
    }

    @RequestMapping(value = "/deleteUserDissapprove")
    String deleteUserDissapprove(
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return userService.deleteUserDissapprove(loggedUsername, model);
    }

}
