package sai_adapa.projs.inv_management.repositories.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Vendor;

import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {
    Vendor findByEmail(String email);

    Vendor findBySessionToken(String token);

    Vendor findByVendorId(UUID uuid);

    Boolean existsVendorBySessionToken(String token);
}
