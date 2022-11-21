package projekt.wsb.linkbinder.service;

import org.springframework.ui.Model;
import projekt.wsb.linkbinder.tables.TableDto;

public interface LinkTablesService {

    String deleteTable(String currentTable, String loggedUsername, Model model);

    String createTable(TableDto tableDto, String loggedUsername, Model model);
}
