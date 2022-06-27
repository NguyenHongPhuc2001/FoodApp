package hcmute.edu.vn.foody.model;

public class Account {
    private int AccID;
    private String Username;
    private String Password;
    private int Role;

    public Account(int AccID, String username, String password, int role) {
        AccID = AccID;
        Username = username;
        Password = password;
        Role = role;
    }

    public int getAccID() {
        return AccID;
    }

    public void setAccID(int accID) {
        AccID = accID;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
