package models;

public class Users {
    private String name;
    private String email;
    private String pass;
    private String imageUser;

    public Users() {
    }

    public Users(String name, String email, String pass, String imageUser) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.imageUser = imageUser;
    }

    public Users(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
