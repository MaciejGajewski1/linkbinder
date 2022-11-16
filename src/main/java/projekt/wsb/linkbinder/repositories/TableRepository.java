package projekt.wsb.linkbinder.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import projekt.wsb.linkbinder.tables.TableEntity;
import projekt.wsb.linkbinder.users.UserEntity;

import java.util.List;

public interface TableRepository extends CrudRepository<TableEntity, String> {

}
