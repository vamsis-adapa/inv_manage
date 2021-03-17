package sai_adapa.projs.inv_management.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.orders.Orders;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderRepository extends MongoRepository<Orders, String> {
    List<Orders> findAllByUserId(UUID userId);

    List<Orders> findAllByVendorId(UUID vendorId);

    Page<Orders> findAllByUserId(UUID uuid, Pageable pageable);

    Page<Orders> findAllByVendorId(UUID uuid, Pageable pageable);

}
