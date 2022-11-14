package projekt.wsb.linkbinder.repositories;

import org.springframework.data.repository.CrudRepository;
import projekt.wsb.linkbinder.users.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

}
