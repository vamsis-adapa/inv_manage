package sai_adapa.projs.inv_management.users.vendor;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Vendor {

    @Id
    private String vendor_id;

    private String name;
    private String e_mail;
    private String description;
    private String passwdHash;



    //constructors getters and setters
    public Vendor() {
    }

    public Vendor(String vendor_id, String name, String e_mail, String description, String passwdHash) {
        this.vendor_id = vendor_id;
        this.name = name;
        this.e_mail = e_mail;
        this.description = description;
        this.passwdHash = passwdHash;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
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

    public void setE_mail(String email_id) {
        this.e_mail = email_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
