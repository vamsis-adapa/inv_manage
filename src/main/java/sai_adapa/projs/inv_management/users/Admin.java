package sai_adapa.projs.inv_management.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Builder
@Entity
@Getter
@NoArgsConstructor
public class Admin {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, nullable = false)
    @Type(type = "pg-uuid")
    private UUID admin_id;
    @Column(unique = true)
    @Setter
    private String email;
    @Setter
    private String passwdHash;
    @Setter
    @Column(unique = true, nullable = true)
    private String sessionToken;


}
