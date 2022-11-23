package projekt.wsb.linkbinder.repositories;

import org.springframework.data.repository.CrudRepository;
import projekt.wsb.linkbinder.tables.TableEntity;

public interface TableRepository extends CrudRepository<TableEntity, String> {

}
