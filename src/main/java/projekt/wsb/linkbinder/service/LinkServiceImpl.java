package projekt.wsb.linkbinder.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekt.wsb.linkbinder.links.KeyWordDto;
import projekt.wsb.linkbinder.links.LinkDto;
import projekt.wsb.linkbinder.links.LinkEntity;
import projekt.wsb.linkbinder.repositories.LinkRepository;
import projekt.wsb.linkbinder.repositories.TableRepository;
import projekt.wsb.linkbinder.repositories.UserRepository;
import projekt.wsb.linkbinder.tables.TableDto;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
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

        Optional<LinkEntity> linkEntityOptional = linkRepository.findById(currentLink);
        if (linkEntityOptional.isPresent()) {
            String tablename = linkEntityOptional.get().getTableEntity().getTablename();
            linkRepository.deleteById(currentLink);
            addModelAttributes(tablename, model, new LinkDto());
            return "opened_table";
        } else {
            UserEntity dbUser = userRepository.findById(loggedUsername).get();
            model.addAttribute("user", dbUser.toDto());
            model.addAttribute("table", new TableDto());
            List<TableDto> usertables = dbUser.getTables().stream().map(s -> s.toDto()).collect(Collectors.toList());
            usertables.sort(new Comparator<TableDto>() {
                @Override
                public int compare(TableDto o1, TableDto o2) {
                    return o1.getTablename().compareToIgnoreCase(o2.getTablename());
                }
            });
            model.addAttribute("usertables", usertables);

            return "logged_user";
        }
    }
    @Override
    public String addLink(LinkDto linkDto, String tablename, Model model) {

        linkDto.setId(linkDto.getId().toLowerCase());

        if (linkDto.getId().isBlank()) {
            addModelAttributes(tablename, model, linkDto);
            return "blank_linkid";
        } else if (linkDto.getTargetUrl().isBlank()) {
            addModelAttributes(tablename, model, linkDto);
            return "blank_targeturl";
        } else if (!isValidURL(linkDto)) {
            addModelAttributes(tablename, model, linkDto);
            return "wrong_url";
        } else if (linkRepository.existsById(linkDto.getId())) {
            addModelAttributes(tablename, model, linkDto);
            return "duplicated_linkid";
        }
        else {
            LinkEntity newLinkEntity = LinkEntity.fromDto(linkDto);
            newLinkEntity.setTableEntity(tableRepository.findById(tablename).get());
            linkRepository.save(newLinkEntity);
            addModelAttributes(tablename, model, linkDto);
            return "opened_table";
        }
    }

    private boolean isValidURL(LinkDto linkDto) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(linkDto.getTargetUrl());
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

    @Override
    public String getLink(final String id) {
        final LinkEntity linkEntity = linkRepository.findById(id).get();
        return linkEntity.getTargetUrl();
    }

    @Override
    public String searchForLink(String tablename, KeyWordDto keyWordDto, Model model) {
        String stringToMatch = keyWordDto.getKeyWord().toLowerCase();
        if (stringToMatch.isBlank()) {
            addModelAttributes(tablename, model, new LinkDto());
            return "blank_search";
        }
        List<LinkDto> linklist = tableRepository.findById(tablename).get().getLinks().stream().map(s -> s.toDto()).collect(Collectors.toList());
        ListIterator<LinkDto> iterator = linklist.listIterator();
        while(iterator.hasNext()) {
            String currentLinkId = iterator.next().getId();
            if (!currentLinkId.contains(stringToMatch))
                iterator.remove();
        }
        linklist.sort(new Comparator<LinkDto>() {
            @Override
            public int compare(LinkDto o1, LinkDto o2) {
                return o1.getId().compareToIgnoreCase(o2.getId());
            }
        });
        model.addAttribute("user", tableRepository.findById(tablename).get().getUserEntity().toDto());
        model.addAttribute("linklist", linklist);
        model.addAttribute("tablename", tablename);
        model.addAttribute("keyWord", keyWordDto);
        return "search_result";
    }

    @Override
    public String redirectToOpenedTable(String tablename, Model model) {
        addModelAttributes(tablename, model, new LinkDto());
        return "opened_table";
    }

    private void addModelAttributes(String tablename, Model model, LinkDto linkDto) {

        UserEntity dbUser = tableRepository.findById(tablename).get().getUserEntity();
        List<LinkDto> linklist = tableRepository.findById(tablename).get().getLinks().stream().map(s -> s.toDto()).collect(Collectors.toList());
        linklist.sort(new Comparator<LinkDto>() {
            @Override
            public int compare(LinkDto o1, LinkDto o2) {
                return o1.getId().compareToIgnoreCase(o2.getId());
            }
        });
        model.addAttribute("user", dbUser.toDto());
        model.addAttribute("linklist", linklist);
        model.addAttribute("tablename", tablename);
        model.addAttribute("link", linkDto);
        model.addAttribute("keyWord", new KeyWordDto());
    }
}
