package sai_adapa.projs.inv_management.model.users;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, nullable = false)
    @Type(type = "pg-uuid")
    private UUID admin_id;
    @Column(unique = true, nullable = false)
    @Setter
    private String email;
    @Setter
    private String passwdHash;
    @Setter
    @Column(unique = true, nullable = true)
    private String sessionToken;


    public Admin(String email, String passwdHash) {
        this.email = email;
        this.passwdHash = passwdHash;
    }
}
