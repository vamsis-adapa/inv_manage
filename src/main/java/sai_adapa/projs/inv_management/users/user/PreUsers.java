package sai_adapa.projs.inv_management.users.user;

public class PreUsers {
    private String name;
    private String e_mail;
    private String details;
    private String passwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public PreUsers(String e_mail, String passwd) {
        this.e_mail = e_mail;
        this.passwd = passwd;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public PreUsers() {
    }

    public PreUsers(String name, String e_mail, String details, String passwd) {
        this.name = name;
        this.e_mail = e_mail;
        this.details = details;
        this.passwd = passwd;
    }

}
