package projekt.wsb.linkbinder.controllers.users;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public final class UserDto {

    @Size(min = 2, max = 15, message = "size of 2 to 15 signs")
    private String username;
    @Size(min = 10, max = 15, message = "size of 10 to 15 signs")
    private String password;
    @Size(min = 2, max = 20, message = "size of 2 to 20 signs")
    private String firstname;
    @Size(min = 2, max = 25, message = "size of 2 to 25 signs")
    private String lastname;
    @Min(value = 12, message = "You must be at least 12 year old to register.")
    private int age;
    @Size(min = 2, max = 30, message = "size of 2 to 30 signs")
    private String email;
}
