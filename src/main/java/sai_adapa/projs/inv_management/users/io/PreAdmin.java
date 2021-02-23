package sai_adapa.projs.inv_management.users.io;

public class PreAdmin {
    private String email;
    private String passwd;

    public PreAdmin() {
    }

    public PreAdmin(String email)
    {
        this.email = email;
    }

    public PreAdmin(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
