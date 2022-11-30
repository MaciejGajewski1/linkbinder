package projekt.wsb.linkbinder.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import projekt.wsb.linkbinder.users.UserDto;

public interface UserService {

    String initModel(Model model);
    String logUser(UserDto user, Model model);
    String beginRegistration(Model model);
    String validateAndAddPerson(UserDto user, BindingResult bindingResult, Model model);
    String returnToHomePage(Model model);
    String deleteUser(String username, Model model);
    String deleteUserDissapprove(String username, Model model);
    String deleteUserApprove(String username, Model model);

}
