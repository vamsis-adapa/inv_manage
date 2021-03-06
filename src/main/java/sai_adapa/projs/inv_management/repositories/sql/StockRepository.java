package sai_adapa.projs.inv_management.repositories.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByVendor(Vendor vendor);

    Stock findStockByVendorAndItem(Vendor vendor, Item item);

    boolean existsStockByVendorAndItem(Vendor vendor, Item item);

    List<Stock> findAllByItem(Item item);

    List<Vendor> findDistinctVendorByItem(Item item);

    Stock findStockById(Long id);
}
