package sai_adapa.projs.inv_management.users.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {

    Users findByEmail(String email);
    Users findBySessionToken(String token);
    Boolean existsUsersBySessionToken(String token);

}

