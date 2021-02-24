package sai_adapa.projs.inv_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sai_adapa.projs.inv_management.model.users.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {

    Users findByEmail(String email);
    Users findBySessionToken(String token);
    Boolean existsUsersBySessionToken(String token);

}

