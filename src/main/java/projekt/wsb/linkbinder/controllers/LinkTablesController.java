package projekt.wsb.linkbinder.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekt.wsb.linkbinder.service.LinkTablesService;
import projekt.wsb.linkbinder.tables.TableDto;

@Controller
@AllArgsConstructor
class LinkTablesController {
    private final LinkTablesService linkTablesService;

    @RequestMapping(value = "/openTable")
    String openTable(
            @RequestParam(value = "currentTable") String currentTable,
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return linkTablesService.openTable(currentTable, loggedUsername, model);
    }

    @RequestMapping(value = "/deleteTable")
    String deleteTable(
            @RequestParam(value = "currentTable") String currentTable,
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return linkTablesService.deleteTable(currentTable, loggedUsername, model);
    }

    @RequestMapping(value = "/createTable")
    String createTable(
            @ModelAttribute("table") TableDto tableDto,
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return linkTablesService.createTable(tableDto, loggedUsername, model);
    }

}
