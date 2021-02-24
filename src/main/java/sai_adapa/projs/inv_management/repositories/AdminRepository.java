package sai_adapa.projs.inv_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    public Admin findByEmail(String email);
    public Admin findBySessionToken(String token);

    public boolean existsAdminBySessionToken(String sessionToken);


}
