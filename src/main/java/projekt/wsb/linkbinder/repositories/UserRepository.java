package projekt.wsb.linkbinder.repositories;

import org.springframework.data.repository.CrudRepository;
import projekt.wsb.linkbinder.controllers.users.UserDto;
import projekt.wsb.linkbinder.controllers.users.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

}
