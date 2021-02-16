package sai_adapa.projs.inv_management.users.admin;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    private String admin_id;

    private String e_mail;
    private String passwdHash;



    //constructors getters and setters
    public Admin() {
    }

    public Admin(String admin_id, String e_mail, String passwd, String passwdHash) {
        this.admin_id = admin_id;
        this.e_mail = e_mail;
        this.passwdHash = passwdHash;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }
}
