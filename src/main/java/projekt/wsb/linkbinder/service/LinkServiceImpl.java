package projekt.wsb.linkbinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekt.wsb.linkbinder.links.LinkDto;
import projekt.wsb.linkbinder.links.LinkEntity;
import projekt.wsb.linkbinder.repositories.LinkRepository;
import projekt.wsb.linkbinder.repositories.TableRepository;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.tables.TableDto;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class LinkServiceImpl implements LinkService {
    @Autowired
    TableRepository tableRepository;
    @Autowired
    LinkRepository linkRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public String deleteLink(String currentLink, String loggedUsername, Model model) {

        String tablename = linkRepository.findById(currentLink).get().getTableEntity().getTablename();
        linkRepository.deleteById(currentLink);
        addModelAttributes(tablename, model);
        return "opened_table";
    }
    @Override
    public String addLink(LinkDto linkDto, String tablename, Model model) {

        if (linkDto.getId().isBlank()) {
            addModelAttributes(tablename, model);
            return "blank_linkid";
        }
        else if (linkRepository.existsById(linkDto.getId())) {
            addModelAttributes(tablename, model);
            return "duplicated_linkid";
        }
        else {
            LinkEntity newLinkEntity = LinkEntity.fromDto(linkDto);
            newLinkEntity.setTableEntity(tableRepository.findById(tablename).get());
            linkRepository.save(newLinkEntity);
            addModelAttributes(tablename, model);
            return "opened_table";
        }
    }
    @Override
    public String redirectToLinkTables(String tablename, Model model) {

        String loggedUsername = tableRepository.findById(tablename).get().getUserEntity().getUsername();
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

        return "logged_user";
    }

    private void addModelAttributes(String tablename, Model model) {

        UserEntity dbUser = tableRepository.findById(tablename).get().getUserEntity();
        List<LinkDto> linklist = tableRepository.findById(tablename).get().getLinks().stream().map(s -> s.toDto()).collect(Collectors.toList());

        model.addAttribute("user", dbUser.toDto());
        model.addAttribute("linklist", linklist);
        model.addAttribute("tablename", tablename);
        model.addAttribute("link", new LinkDto());
    }
}
