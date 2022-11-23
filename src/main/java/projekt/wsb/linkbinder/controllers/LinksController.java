package projekt.wsb.linkbinder.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekt.wsb.linkbinder.links.LinkDto;
import projekt.wsb.linkbinder.service.LinkService;

@Controller
@AllArgsConstructor
class LinksController {
    private final LinkService linkService;

    @RequestMapping(value = "deleteLink")
    String deleteLink(
            @RequestParam(value = "currentLink") String currentLink,
            @RequestParam(value = "loggedUsername") String loggedUsername,
            Model model) {
        return linkService.deleteLink(currentLink, loggedUsername, model);
    }

    @RequestMapping(value = "addLink")
    String addLink(
            @ModelAttribute("link") LinkDto linkDto,
            @RequestParam(value = "tablename") String tablename,
            Model model) {
        return linkService.addLink(linkDto, tablename, model);
    }

    @RequestMapping(value = "backToLinkTables")
    String redirectToLinkTables(
            @RequestParam(value = "tablename") String tablename,
            Model model) {
        return linkService.redirectToLinkTables(tablename, model);
    }
}
