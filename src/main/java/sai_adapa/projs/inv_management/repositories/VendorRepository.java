package sai_adapa.projs.inv_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.users.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,String > {
    Vendor findByEmail(String email);
    Vendor findBySessionToken(String token);
    Boolean existsVendorBySessionToken(String token);
}
