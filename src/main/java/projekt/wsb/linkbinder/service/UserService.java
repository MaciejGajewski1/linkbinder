package projekt.wsb.linkbinder.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import projekt.wsb.linkbinder.users.UserDto;

public interface UserService {

    String beginRegistration(Model model);

    String validateAndAddPerson(UserDto user, BindingResult bindingResult, Model model);

}
