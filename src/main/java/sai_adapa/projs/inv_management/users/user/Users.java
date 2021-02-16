package sai_adapa.projs.inv_management.users.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {

    @Id
    private String user_id;

    private String name;
    private String e_mail;
    private String details;
    private String passwdHash;


    //constructors getters and setters
    public Users() {
    }

    public Users(String user_id, String name, String e_mail, String details, String passwdHash) {
        this.user_id = user_id;
        this.name = name;
        this.e_mail = e_mail;
        this.details = details;
        this.passwdHash = passwdHash;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
