package com.gmail.stepan1983.DTO;

import com.gmail.stepan1983.model.Client;
import com.gmail.stepan1983.model.ClientGroup;
import com.gmail.stepan1983.model.UserRole;

public class ClientDTO {

    private long id;

    private String login;

    private String email;

    private String phone;

    private String adress;

    private String name;

    private String lastname;

    private UserRole role;

    private String clientGroup;

    private String avatar="http://localhost:8080/photo/"+id;


    public ClientDTO(long id,String login, String email,
                     String phone, String adress, String name,
                     String lastname, UserRole role, String clientGroup) {
       this.id=id;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.clientGroup = clientGroup;
    }

    public ClientDTO() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getClientGroup() {
        return clientGroup;
    }

    public void setClientGroup(String clientGroup) {
        this.clientGroup = clientGroup;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                ", clientGroup='" + clientGroup + '\'' +
                ", avatar='" + avatar + '\'' +
                '}'+"\r\n";
    }
}
