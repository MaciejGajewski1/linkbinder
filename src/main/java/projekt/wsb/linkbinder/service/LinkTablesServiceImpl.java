package projekt.wsb.linkbinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekt.wsb.linkbinder.repositories.TableRepository;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.tables.TableDto;
import projekt.wsb.linkbinder.tables.TableEntity;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class LinkTablesServiceImpl implements LinkTablesService {

    @Autowired
    TableRepository tableRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String deleteTable(String currentTable, String loggedUsername, Model model) {
        tableRepository.deleteById(currentTable);
        addModelAttributes(loggedUsername, model);
        model.addAttribute("table", new TableDto());
        return "logged_user";
    }

    @Override
    public String createTable(TableDto tableDto, String loggedUsername, Model model) {
        if (tableRepository.existsById(tableDto.getTablename())) {
            addModelAttributes(loggedUsername, model);
            return "duplicated_tablename";
        }
        else {
            TableEntity newTableEntity = new TableEntity(
                    tableDto.getTablename(),
                    tableDto.getDescription(),
                    userRepository.findById(loggedUsername).get());
            tableRepository.save(newTableEntity);
            addModelAttributes(loggedUsername, model);
            return "logged_user";
        }
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
    }
}
