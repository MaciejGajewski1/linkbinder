package projekt.wsb.linkbinder.links;

import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projekt.wsb.linkbinder.tables.TableEntity;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@ToString
public final class LinkDto {

    private String id;

    private String description;

    private String targetUrl;

    private String shortenedUrl;

    private TableEntity tableEntity;

    public LinkDto(String id, String description, String targetUrl, TableEntity tableEntity) {
        this.id = id;
        this.description = description;
        this.targetUrl = targetUrl;
        this.tableEntity = tableEntity;
        shortenedUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/s/{id}")
                .buildAndExpand(this.id)
                .toUriString();
    }
}
