package sai_adapa.projs.inv_management.users.admin;

import javax.persistence.*;

@Entity
public class Admin {

    @Id
    @GeneratedValue( generator = "identity")
    private Long admin_id;

    @Column(unique = true)
    private String email;
    private String passwdHash;
    @Column(unique = true,nullable = true)
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

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
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
