package projekt.wsb.linkbinder.tables;

import lombok.*;
import projekt.wsb.linkbinder.users.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public final class TableDto {

    private String tablename;

    private String description;

    private UserEntity userEntity;
}
