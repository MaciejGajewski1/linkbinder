package projekt.wsb.linkbinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.users.UserDto;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.Optional;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String initModel(Model model) {
        model.addAttribute("user", new UserDto());
        return "index";
    }

    @Override
    public String logUser(UserDto user, Model model) {

        final Optional<UserEntity> optionalUser = userRepository.findById(user.getUsername());

        if (optionalUser.isPresent()) {
            UserEntity dbUser = optionalUser.get();
            if (dbUser.getPassword().equals(user.getPassword())) {
                return "logged_user";
            }
            else
                return "wrong_password";
        }
        else
            return "wrong_username";

    }

    @Override
    public String beginRegistration(Model model) {
        model.addAttribute("user", new UserDto());
        return "register_form";
    }

    @Override
    public String validateAndAddPerson(UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register_form";
        }

        UserDto newUser = (UserDto) model.getAttribute("user");

        if(userRepository.findById(newUser.getUsername()).isPresent()) {
            return "redirect:/register_dissapprove";
        }

        userRepository.save(UserEntity.fromDto(newUser));

        return "redirect:/register_approve";
    }

    @Override
    public String returnToHomePage(Model model) {
        model.addAttribute("user", new UserDto());
        return "index";
    }
}
