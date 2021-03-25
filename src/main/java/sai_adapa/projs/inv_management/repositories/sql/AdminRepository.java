package sai_adapa.projs.inv_management.repositories.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByEmail(String email);

    Admin findBySessionToken(String token);

    boolean existsAdminBySessionToken(String sessionToken);


}
