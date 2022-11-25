package projekt.wsb.linkbinder.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.wsb.linkbinder.links.KeyWordDto;
import projekt.wsb.linkbinder.links.LinkDto;
import projekt.wsb.linkbinder.service.LinkService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @GetMapping(path = "/s/{id}")
    public void redirectLink(
            @PathVariable String id,
            HttpServletResponse httpServletResponse) throws IOException {
        String targetUrl = linkService.getLink(id);
        httpServletResponse.sendRedirect(targetUrl);
    }

    @GetMapping("/searchForLink")
    String showMatchedLinks(
            @RequestParam(value = "tablename") String tablename,
            @ModelAttribute("keyWord") KeyWordDto keyWordDto,
            Model model) {
        return linkService.searchForLink(tablename, keyWordDto, model);
    }

    @GetMapping("/backToOpenedTable")
    String redirectToOpenedTable(
            @RequestParam(value = "tablename") String tablename,
            Model model) {
        return linkService.redirectToOpenedTable(tablename, model);
    }
}
