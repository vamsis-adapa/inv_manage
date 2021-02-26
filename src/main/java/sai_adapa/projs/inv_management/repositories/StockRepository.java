package sai_adapa.projs.inv_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.Stock;
import sai_adapa.projs.inv_management.model.users.Vendor;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

public List<Stock> findAllByVendor(Vendor vendor);
public Stock findStockByVendorAndItem(Vendor vendor, Item item);
public  boolean existsStockByVendorAndItem(Vendor vendor, Item item);

}
