package sai_adapa.projs.inv_management.repositories.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Users;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {

    Users findByEmail(String email);
    Users findBySessionToken(String token);
    Users findByUserId(UUID uuid);
    Boolean existsUsersBySessionToken(String token);

}

