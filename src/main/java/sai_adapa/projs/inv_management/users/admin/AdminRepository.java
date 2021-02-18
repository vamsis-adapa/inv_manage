package sai_adapa.projs.inv_management.users.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    public Admin findByEmail(String email);
    public Admin findBySessionToken(String token);

    public boolean existsAdminBySessionToken(String sessionToken);


}