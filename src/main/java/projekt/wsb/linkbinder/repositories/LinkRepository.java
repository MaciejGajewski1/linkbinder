package projekt.wsb.linkbinder.repositories;

import org.springframework.data.repository.CrudRepository;
import projekt.wsb.linkbinder.links.LinkEntity;

public interface LinkRepository extends CrudRepository<LinkEntity, String> {

}
