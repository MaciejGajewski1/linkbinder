package projekt.wsb.linkbinder.service;

import org.springframework.ui.Model;
import projekt.wsb.linkbinder.links.LinkDto;

public interface LinkService {
    String deleteLink(String currentLink, String loggedUsername, Model model);
    String addLink(LinkDto linkDto, String tablename, Model model);
    String redirectToLinkTables(String tablename, Model model);
    String getLink(final String id);
}
