package projekt.wsb.linkbinder.users;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {

    @Id
    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email", nullable = false)
    private String email;

    public static UserEntity fromDto(UserDto userDto) {
        return new UserEntity(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getAge(),
                userDto.getEmail()
        );
    }

    public UserDto toDto() {
        return new UserDto(
                this.username,
                this.password,
                this.firstname,
                this.lastname,
                this.age,
                this.email
        );
    }
}
