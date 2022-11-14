package projekt.wsb.linkbinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.users.UserDto;
import projekt.wsb.linkbinder.users.UserEntity;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String beginRegistration(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
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
}
