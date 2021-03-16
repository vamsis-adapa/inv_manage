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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, nullable = false)
    @Type(type = "pg-uuid")
    private UUID userId;

    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String email;
    private String details;
    @Column(nullable = false)
    private String passwdHash;
    private String sessionToken;

}



