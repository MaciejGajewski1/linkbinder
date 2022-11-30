package projekt.wsb.linkbinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import projekt.wsb.linkbinder.repositories.TableRepository;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.tables.TableDto;
import projekt.wsb.linkbinder.users.UserDto;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableRepository tableRepository;

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
                addModelAttributes(user.getUsername(), model);
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

        if(!validatePassword(newUser.getPassword()))
            return "redirect:/password_dissapprove";

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

    @Override
    public String deleteUser(String username, Model model) {
        addModelAttributes(username, model);
        return "delete_account_message";
    }

    @Override
    public String deleteUserDissapprove(String username, Model model) {
        addModelAttributes(username, model);
        return "logged_user";
    }

    @Override
    public String deleteUserApprove(String username, Model model) {
        userRepository.deleteById(username);
        return initModel(model);
    }

    private void addModelAttributes(String loggedUsername, Model model) {
        UserEntity dbUser =  userRepository.findById(loggedUsername).get();
        List<TableDto> usertables = dbUser.getTables().stream().map(s -> s.toDto()).collect(Collectors.toList());
        usertables.sort(new Comparator<TableDto>() {
            @Override
            public int compare(TableDto o1, TableDto o2) {
                return o1.getTablename().compareToIgnoreCase(o2.getTablename());
            }
        });
        model.addAttribute("usertables", usertables);
        model.addAttribute("user", dbUser.toDto());
        model.addAttribute("table", new TableDto());
    }

    private boolean validatePassword(String password) {
        List<Character> numbers = Arrays.asList('0','1','2','3','4','5','6','7','8','9');
        List<Character> specials = Arrays.asList('!','@','#','$','%','^','&','*','?','.');
        boolean numFlag = false;
        boolean specFlag = false;
        for(char c : password.toCharArray()) {
            if (numbers.contains(c))
                numFlag = true;
            if (specials.contains(c))
                specFlag = true;
        }
        return numFlag & specFlag;
    }
}
