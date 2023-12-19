/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class User extends Role{
    private String username;
    private String password;
    
    public User(String role, String nickname, String password){
        super(role);
        this.username = nickname;
        this.password = password;
    }

    public String getNickname() {
        return username;
    }

    public void setNickname(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
