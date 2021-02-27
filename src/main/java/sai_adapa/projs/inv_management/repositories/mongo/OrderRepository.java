package sai_adapa.projs.inv_management.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.orders.Orders;


@Repository
public interface OrderRepository extends MongoRepository<Orders, String> {
}
