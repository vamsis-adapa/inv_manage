package sai_adapa.projs.inv_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.items.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
