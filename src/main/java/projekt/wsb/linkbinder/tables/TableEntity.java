package projekt.wsb.linkbinder.tables;

import lombok.*;
import projekt.wsb.linkbinder.links.LinkEntity;
import projekt.wsb.linkbinder.users.UserEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TableEntity {

    @Id
    @Column(name = "tablename", nullable = false, updatable = false)
    private String tablename;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "tableEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LinkEntity> links;

    public TableEntity(String tablename, String description, UserEntity userEntity) {
        this.tablename = tablename;
        this.description = description;
        this.userEntity = userEntity;
        links = new HashSet<>();
    }

    public static TableEntity fromDto(TableDto tableDto) {
        return new TableEntity(
                tableDto.getTablename(),
                tableDto.getDescription(),
                tableDto.getUserEntity()
        );
    }

    public TableDto toDto() {
        return new TableDto(
                this.tablename,
                this.description,
                this.userEntity
        );
    }
}
