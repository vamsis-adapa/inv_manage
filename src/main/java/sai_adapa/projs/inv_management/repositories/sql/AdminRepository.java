package sai_adapa.projs.inv_management.repositories.sql;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Cacheable(value = "admin")
    public Admin findByEmail(String email);

    @Cacheable(value = "admin_session")
    public Admin findBySessionToken(String token);

    public boolean existsAdminBySessionToken(String sessionToken);


}
