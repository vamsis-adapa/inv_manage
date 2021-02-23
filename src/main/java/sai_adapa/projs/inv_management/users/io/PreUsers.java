package sai_adapa.projs.inv_management.users.io;

public class PreUsers {
    private String name;
    private String email;
    private String details;
    private String passwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public PreUsers(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public PreUsers() {
    }

    public PreUsers(String name, String email, String details, String passwd) {
        this.name = name;
        this.email = email;
        this.details = details;
        this.passwd = passwd;
    }

}
