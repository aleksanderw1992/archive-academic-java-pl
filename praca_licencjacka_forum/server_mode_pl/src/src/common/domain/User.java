/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

/**
 *
 * @author Aleksander Wojcik
 */
public class User {

    public static final String INSERT = getInsert();
    public static final String TABLE = "users";

    private int user_id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", login=" + login + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + '}';
    }

    public User(int user_id, String login, String password, String firstname, String lastname, String email) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(" ")
                .append(User.TABLE)
                .append("(")
                .append("login,")
                .append("password,")
                .append("firstname,")
                .append("lastname,")
                .append("email")
                .append(") ");
        return stringBuilder.toString();
    }
}
