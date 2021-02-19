package sai_adapa.projs.inv_management.users.admin;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Admin {

    //    @Id
//    @GeneratedValue(generator = "identity")
//    private Long admin_id;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, nullable = false)
    @Type(type = "pg-uuid")
    private UUID admin_id;
    @Column(unique = true)
    private String email;
    private String passwdHash;
    @Column(unique = true, nullable = true)
    private String sessionToken;

    //constructors getters and setters
    public Admin() {
    }

    public Admin(String email, String passwdHash) {
        this.email = email;
        this.passwdHash = passwdHash;
    }


    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String session_token) {
        this.sessionToken = session_token;
    }

    public UUID getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(UUID admin_id) {
        this.admin_id = admin_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e_mail) {
        this.email = e_mail;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }
}
