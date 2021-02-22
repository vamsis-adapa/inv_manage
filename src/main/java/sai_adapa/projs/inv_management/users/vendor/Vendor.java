package sai_adapa.projs.inv_management.users.vendor;


import javax.persistence.*;


@Entity
public class Vendor {

    @Id
    @GeneratedValue( generator = "identity")
    private Long vendor_id;

    private String name;

    @Column(unique = true)
    private String email;
    private String description;
    private String passwdHash;
    private String sessionToken;

    //constructors getters and setters
    public Vendor() {
    }

    public Vendor(String name, String email, String description, String passwdHash) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.passwdHash = passwdHash;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email_id) {
        this.email = email_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
