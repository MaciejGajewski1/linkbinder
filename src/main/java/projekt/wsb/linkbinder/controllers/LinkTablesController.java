package projekt.wsb.linkbinder.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekt.wsb.linkbinder.repositories.TableRepository;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.tables.TableDto;
import projekt.wsb.linkbinder.tables.TableEntity;
import projekt.wsb.linkbinder.users.UserEntity;

import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
class LinkTablesController {

    @Autowired
    TableRepository tableRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/deleteTable")
    String deleteRow(@RequestParam(value = "currentTable") String currentTable,
                     @RequestParam(value = "loggedUsername") String loggedUsername,
                     Model model) {
        tableRepository.deleteById(currentTable);
        UserEntity dbUser =  userRepository.findById(loggedUsername).get();
        List<TableDto> usertables = dbUser.getTables().stream().map(s -> s.toDto()).collect(Collectors.toList());
        model.addAttribute("usertables", usertables);
        model.addAttribute("user", dbUser.toDto());
        model.addAttribute("table", new TableDto());
        return "logged_user";
    }

    @RequestMapping(value = "/createTable")
    String createTable(@ModelAttribute("table") TableDto tableDto,
                       @RequestParam(value = "loggedUsername") String loggedUsername,
                       Model model) {
        if (tableRepository.existsById(tableDto.getTablename())) {
            UserEntity dbUser =  userRepository.findById(loggedUsername).get();
            List<TableDto> usertables = dbUser.getTables().stream().map(s -> s.toDto()).collect(Collectors.toList());
            model.addAttribute("usertables", usertables);
            model.addAttribute("user", dbUser.toDto());
            return "duplicated_tablename";
        }
        else {
            TableDto newTableDto = (TableDto) model.getAttribute("table");
            TableEntity newTableEntity = new TableEntity(tableDto.getTablename(),
                    tableDto.getDescription(),
                    userRepository.findById(loggedUsername).get());
            tableRepository.save(newTableEntity);
            UserEntity dbUser =  userRepository.findById(loggedUsername).get();
            List<TableDto> usertables = dbUser.getTables().stream().map(s -> s.toDto()).collect(Collectors.toList());
            model.addAttribute("usertables", usertables);
            model.addAttribute("user", dbUser.toDto());
            return "logged_user";
        }
    }

}
