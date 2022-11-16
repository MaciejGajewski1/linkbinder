package projekt.wsb.linkbinder.tables;

import lombok.*;
import projekt.wsb.linkbinder.users.UserEntity;

import javax.persistence.*;

@Entity(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TableEntity {

    @Id
    @Column(name = "tablename", nullable = false, updatable = false)
    private String tablename;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;

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
