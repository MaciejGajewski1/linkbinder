package projekt.wsb.linkbinder.links;

import lombok.*;
import projekt.wsb.linkbinder.tables.TableEntity;

import javax.persistence.*;

@Entity
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LinkEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "targetUrl", nullable = false)
    private  String targetUrl;

    @JoinColumn(name = "tablename", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private TableEntity tableEntity;

    public static LinkEntity fromDto(LinkDto linkDto) {
        return new LinkEntity(
                linkDto.getId(),
                linkDto.getTargetUrl(),
                linkDto.getTableEntity()
        );
    }

    public LinkDto toDto() {
        return new LinkDto(
                this.id,
                this.targetUrl,
                this.tableEntity
        );
    }
}
