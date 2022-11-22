package projekt.wsb.linkbinder.links;

import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projekt.wsb.linkbinder.tables.TableEntity;

@NoArgsConstructor
@Getter
@Setter
@ToString
public final class LinkDto {

    private String id;

    private String targetUrl;

    private String shortenedUrl;

    private TableEntity tableEntity;

    public LinkDto(String id, String targetUrl, TableEntity tableEntity) {
        this.id = id;
        this.targetUrl = targetUrl;
        this.tableEntity = tableEntity;
        shortenedUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(this.id)
                .toUriString();
    }

}
